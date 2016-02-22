package com.ruslan.mentoring.SQL.task01;

import com.ruslan.mentoring.SQL.util.SqlUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BatchQueryExecutor extends Thread {
    private Options options;

    public BatchQueryExecutor(Options options) {
        this.options = options;
    }

    @Override
    public void run() {
        while (!options.getQueue().isEmpty() || options.isHasMoreElements()) {
            try {
                List<String> batchUpdateParts = options.getQueue().poll(1, TimeUnit.SECONDS);
                if (batchUpdateParts != null) {
                    SqlUtil.executeBatch(options.getDataSource(),
                            batchUpdateParts.toArray(new String[batchUpdateParts.size()]));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}