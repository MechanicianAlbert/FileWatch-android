package com.albertech.filehelper.core.query.cursor.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.albertech.filehelper.core.query.cursor.ICursorFactory;

/**
 * 游标抽象工厂基础实现
 */
public abstract class AbsCursorFactory implements ICursorFactory {

    private boolean mNeedRecursive;

    public AbsCursorFactory() {

    }

    public AbsCursorFactory(boolean recursive) {
        mNeedRecursive = recursive;
    }


    @Override
    public Cursor createCursor(Context context, String[] projection, String parentPath) {
        return context.getContentResolver().query(
                uri(),
                createProjection(projection),
                createSelection(),
                createSelectionArgsByParentPath(parentPath),
                null
        );
    }

    /**
     * 获取查询字段
     * @param projection 查询字段
     * @return 查询字段
     */
    protected String[] createProjection(String[] projection) {
        // 字段不指定时只查询路径字段
        return projection != null ? projection : new String[]{PATH_COLUMN_NAME};
    }

    /**
     * 获取查询条件字段
     * @return 查询条件字段
     */
    protected String createSelection() {
        return createSelection(recursive());
    }

    /**
     * 根据父路径, 获取查询条件表达式
     * @param parentPath
     * @return
     */
    protected String[] createSelectionArgsByParentPath(String parentPath) {
        return createSelectionArgsByParentPath(parentPath, recursive());
    }

    /**
     * @return 是否递归查询
     */
    protected boolean recursive() {
        return mNeedRecursive;
    }

    /**
     * 查询条件表达式
     * @return
     */
    protected String[] selectionArgs() {
        return null;
    }

    /**
     * 根据是否递归查询, 获取查询条件字段
     * @param recursive 是否递归查询
     * @return 查询条件字段
     */
    private String createSelection(boolean recursive) {
        StringBuffer b = new StringBuffer("");
        // 获得查询条件数组模板
        String[] template = selectionArgs();
        // 数组模板是否有内容, 有内容即为多条件查询
        boolean templateAvailable = template != null && template.length > 0;
        // 将数组展开拼接为sql语句
        if (templateAvailable) {
            // 数组模板有内容
            if (recursive) {
                // 递归
                // 使用 LIKE 约束查询路径
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND (");
            } else {
                // 非递归
                // 使用 LIKE 约束查询路径
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND ");
                // 使用 NOT LIKE 约束不查询此路径的任意子路径
                b.append(PATH_COLUMN_NAME).append(" NOT LIKE ? AND (");
            }
            // 遍历数组模板, 将条件拼接好
            for (int i = 0; i < template.length - 1; i++) {
                // 数组模板中的内容, 均为文件后缀, 用于匹配某种文件类型, 相互间为 [或] 的并列关系.
                b.append(PATH_COLUMN_NAME).append(" LIKE ? OR ");
            }
            b.append(PATH_COLUMN_NAME).append(" LIKE ? )");
        } else {
            // 数组模板无内容
            if (recursive) {
                // 递归
                // 仅指定查询路径
                b.append(PATH_COLUMN_NAME).append(" LIKE ? ");
            } else {
                // 非递归
                // 指定查询路径
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND ");
                // 指定不查询此路径的任意子路径
                b.append(PATH_COLUMN_NAME).append(" NOT LIKE ? ");
            }
        }
        String selection = b.toString();
        return selection;
    }

    /**
     * 根据是否递归查询, 获取查询条件表达式
     * @param parentPath 查询路径
     * @param recursive 是否递归查询
     * @return 查询条件表达式
     */
    private String[] createSelectionArgsByParentPath(String parentPath, boolean recursive) {
        // 获得查询条件数组模板
        String[] templateArgs = selectionArgs();
        // 数组模板是否有内容, 有内容即为多条件查询
        boolean templateAvailable = templateArgs != null && templateArgs.length > 0;
        // 条件表达式模板长度, 多条件时为模板长度, 否则为0
        int templateLength = templateAvailable ? templateArgs.length : 0;
        // 额外增加的基础条件长度
        // 递归为1, 增加的1个条件为约束查询路径
        // 非递归为2, 增加的2给条件, 第一个为约束查询路径, 第二个为不查询任意子路径
        int additionalArgCount = recursive ? 1 : 2;
        // 查询条件表达式数组, 长度为模板长度加基础条件长度
        String[] selectionArgs = new String[templateLength + additionalArgCount];
        // 指定第一个约束条件为查询路径
        selectionArgs[0] = createParentPathConditionArg(parentPath);
        if (!recursive) {
            // 非递归查询, 指定第二个约束条件为不查询任意子路径
            selectionArgs[1] = createDisableRecursiveConditionArg(parentPath);
        }
        // 数组模板不为空
        if (templateAvailable) {
            // 填充查询条件表达式数组
            for (int i = 0; i < templateLength; i++) {
                selectionArgs[i + additionalArgCount] = createParentPathConditionArg(parentPath) + templateArgs[i];
            }
        }
        return selectionArgs;
    }

    /**
     * 使用路径生成 [查询路径] 的查询条件表达式, 用于约束查询路径
     * @param path 查询路径
     * @return 查询条件表达式
     */
    private String createParentPathConditionArg(String path) {
        return path + "/%";
    }

    /**
     * 使用路径生成 [查询路径的任意子路径] 的查询条件表达式, 用于在非递归时, 约束不查询此路径的任意子路径
     * @param path 查询路径
     * @return 查询条件表达式
     */
    private String createDisableRecursiveConditionArg(String path) {
        return path + "/%/%";
    }

    /**
     * 访问内容提供者需要提供查询Uri
     * @return uri
     */
    protected abstract Uri uri();

}
