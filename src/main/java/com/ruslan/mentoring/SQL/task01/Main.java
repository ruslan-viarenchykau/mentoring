package com.ruslan.mentoring.SQL.task01;

import com.ruslan.mentoring.SQL.util.SqlUtil;
import com.ruslan.mentoring.SQL.util.type.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Main class.
 * The main(String[]) method of the class copies a database, using provided parameters from the command line.
 * See description of the parameters below (by index):
 *
 * 0 - connectionUrl. Sample connectionUrl: "jdbc:postgresql://localhost:5432/mydatabase?user=user&password=password"
 * 1 - rowsOrder. direct or reverse order of rows
 *
 * Example command line args:
 * "jdbc:postgresql://localhost:5432/mydatabase?user=user&password=password" "reverse"
 */
public class Main {
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private static final String COPY_NAME = "database_copy";
    private static final String REVERSE_ORDER = "reverse";

    private static final String[] TABLE_TYPES = {"TABLE"};
    private static final String SCHEMA_PATTERN = "public";
    private static final int threadsNumber = 20;

    private static String connectionUrl;
    private static String rowsOrder;
    private static DataSource sourceDataSource;
    private static DataSource targetDataSource;

    public static void main(String[] args) throws Exception {
        init(args);

        long startTime = new Date().getTime();
        copyDatabase();
        long endTime = new Date().getTime();

        System.out.println("Total time: " + (endTime - startTime));
    }

    private static void init(String[] args) throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        connectionUrl = args[0];
        rowsOrder = args[1];
        sourceDataSource = SqlUtil.setupDataSource(connectionUrl);
        SqlUtil.executeBatch(sourceDataSource, "DROP DATABASE IF EXISTS " + COPY_NAME, "CREATE DATABASE " + COPY_NAME);
        targetDataSource = SqlUtil.setupDataSource(connectionUrl.replaceAll("/\\w+\\?", "/" + COPY_NAME + "?"));
    }

    private static void copyDatabase() throws InterruptedException, SQLException {

        Map<String, List<Column>> metadata = extractMetadata();
        Options options = new Options(targetDataSource, new ArrayBlockingQueue<List<String>>(10));
        List<Thread> threads = startThreads(options);

        for (Map.Entry<String, List<Column>> entry: metadata.entrySet()) {
            String tableName = entry.getKey();
            List<Column> columns = entry.getValue();

            String createTableSql = SqlUtil.generateCreateTableSql(tableName, columns);
            Thread tableCreatorThread = new QueryExecutor(createTableSql);
            tableCreatorThread.start();
            try (
                    Connection connection = sourceDataSource.getConnection();
                    Statement statement = connection.createStatement();
            ) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
                List<String> batchUpdateParts = createBatchUpdateParts(resultSet, columns.size(), tableName);
                if (REVERSE_ORDER.equals(rowsOrder)) {
                    Collections.reverse(batchUpdateParts);
                }
                tableCreatorThread.join();
                options.getQueue().put(batchUpdateParts);
            }
        }
        options.setHasMoreElements(false);
        awaitTermination(threads);
    }

    private static List<String> createBatchUpdateParts(ResultSet resultSet, int valuesNumber, String tableName)
            throws SQLException {

        List<String> batchParts = new ArrayList<>();
        while (resultSet.next()) {
            List<String> values = new ArrayList<>(valuesNumber);
            for (int i = 1; i <= valuesNumber; i++) {
                values.add(resultSet.getString(i));
            }
            batchParts.add(SqlUtil.generateInsertSql(tableName, values));
        }
        return batchParts;
    }

    private static Map<String, List<Column>> extractMetadata() {
        Map<String, List<Column>> metadata = new HashMap<>();
        try {
            DatabaseMetaData dbMetadata = sourceDataSource.getConnection().getMetaData();
            ResultSet tablesMetadata = dbMetadata.getTables(null, SCHEMA_PATTERN, null, TABLE_TYPES);
            // Already ordered by tableName
            while (tablesMetadata.next()) {
                List<Column> columns = new ArrayList<>();
                String tableName = tablesMetadata.getString(3);
                ResultSet columnsMetadata = dbMetadata.getColumns(null, SCHEMA_PATTERN, tableName, null);
                // Ordered by index
                while (columnsMetadata.next()) {
                    String columnName = columnsMetadata.getString(4);
                    String dataSourceTypeName = columnsMetadata.getString(6);
                    final String size = columnsMetadata.getString(16);

                    Column column = new Column(columnName, new ColumnType(Type.fromDataSourceName(dataSourceTypeName)){{
                        if (this.getType().isSizable()) {
                            setSize(Integer.parseInt(size));
                        }
                    }});
                    columns.add(column);
                }
                metadata.put(tableName, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metadata;
    }

    private static List<Thread> startThreads(Options options) {
        List<Thread> threads = new ArrayList<>(threadsNumber);
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new BatchQueryExecutor(options));
            thread.start();
            threads.add(thread);
        }
        return threads;
    }

    private static void awaitTermination(List<Thread> threads) throws InterruptedException {
        for (Thread thread: threads) {
            thread.join();
        }
    }

    static class QueryExecutor extends Thread {
        private String sql;

        public QueryExecutor(String sql) {
            this.sql = sql;
        }

        @Override
        public void run() {
            SqlUtil.executeUpdate(sql, targetDataSource);
        }
    }
}
