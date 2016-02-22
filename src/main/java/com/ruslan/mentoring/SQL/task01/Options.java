package com.ruslan.mentoring.SQL.task01;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Options {
    private DataSource dataSource;
    private boolean hasMoreElements;
    private BlockingQueue<List<String>> queue;

    public Options(DataSource dataSource, BlockingQueue<List<String>> queue) {
        this.dataSource = dataSource;
        this.queue = queue;
        this.hasMoreElements = true;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public boolean isHasMoreElements() {
        return hasMoreElements;
    }

    public BlockingQueue<List<String>> getQueue() {
        return queue;
    }

    public void setHasMoreElements(boolean hasMoreElements) {
        this.hasMoreElements = hasMoreElements;
    }
}
