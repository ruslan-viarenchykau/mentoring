package com.ruslan.mentoring.SQL.task02;

import com.ruslan.mentoring.SQL.util.SqlUtil;

import java.util.concurrent.TimeUnit;

public class DataGenerator implements Runnable {
    private GeneratorOptions options;

    public DataGenerator(GeneratorOptions options) {
        this.options = options;
    }

    @Override
    public void run() {
        while (!options.getSqlQueries().isEmpty() || options.isHasMoreQueries()) {
            try {
                String sqlQuery = options.getSqlQueries().poll(1, TimeUnit.SECONDS);
                if (sqlQuery != null) {
                    SqlUtil.executeUpdate(sqlQuery, options.getDataSource());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
