package com.albertech.filehelper.core.scan.commit;

/**
 * 扫描任务提交接口, 可以主动向系统提交扫描媒体库的任务
 */
public interface IScanCommit {

    void commitScanMission(String...paths);
}
