package com.albertech.filehelper.core.scan;

import com.albertech.filehelper.core.scan.task.IFileScanTaskGroup;
import com.albertech.filehelper.core.scan.task.OnceCommitOnceReportScanTaskGroup;
import com.albertech.filehelper.api.IFileConstant;


public class ScanTaskGroupFactory {

    private static int sScanBatchSize = 20;


    public static IFileScanTaskGroup create(String path) {
        if (isUsbPath(path)) {
//            return new EachCommitBatchReportScanTaskGroup(sScanBatchSize);
            return new OnceCommitOnceReportScanTaskGroup();
        } else {
            return new OnceCommitOnceReportScanTaskGroup();
        }
    }

    public static void setScanBatchSize(int size) {
        if (size <= 0) {
            return;
        }
        sScanBatchSize = size;
    }


    private static boolean isUsbPath(String path) {
        return path != null && !path.startsWith(IFileConstant.SD_CARD_PATH);
    }

}
