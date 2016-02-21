package com.ruslan.mentoring.SQL.task02;

import javax.sql.DataSource;
import java.util.concurrent.BlockingQueue;

public class GeneratorOptions {
    private boolean hasMoreQueries;
    private DataSource dataSource;
    private BlockingQueue<String> sqlQueries;

    public GeneratorOptions(DataSource dataSource, BlockingQueue<String> sqlQueries) {
        this.dataSource = dataSource;
        this.sqlQueries = sqlQueries;
        this.hasMoreQueries = true;
    }

    public boolean isHasMoreQueries() {
        return hasMoreQueries;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public BlockingQueue<String> getSqlQueries() {
        return sqlQueries;
    }

    public void setHasMoreQueries(boolean hasMoreQueries) {
        this.hasMoreQueries = hasMoreQueries;
    }
}
