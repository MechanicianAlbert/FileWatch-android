package com.albertech.filehelper.core.scan.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件目录树工具类
 */
class PathUtil {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 将文件目录树中的所有路径的扁平化为一维数组
     * @param rootPath 文件目录树根路径
     * @return 目录数组
     */
    static String[] getPathTreeArray(String rootPath) {
        File f = new File(rootPath);
        List<String> list = new ArrayList<>();
        PathUtil.addSubPathRecursively(list, f);
        return list.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * 向集合中递归添加某文件的路径及其所有子文件路径
     *
     * @param list 路径集合
     * @param f    文件
     */
    private static void addSubPathRecursively(List<String> list, File f) {
        if (f != null && list != null) {
            list.add(f.getAbsolutePath());
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    addSubPathRecursively(list, file);
                }
            }
        }
    }

}
