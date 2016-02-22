package com.ruslan.mentoring.SQL.task02;

import com.ruslan.mentoring.SQL.util.SqlUtil;
import com.ruslan.mentoring.SQL.util.type.Column;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Main class.
 * The main(String[]) method of the class fills a database table with generated data, using provided parameters
 * from the command line. See description of the parameters below (by index):
 *
 * 0 - connectionUrl. Sample connectionUrl: "jdbc:postgresql://localhost:5432/mydatabase?user=user&password=password"
 * 1 - tablesNumber. The number of tables to create
 * 2 - columnsNumber. The number of columns to add to the created table with random types
 * 3 - rowsNumber. The number of rows to add to each table
 * 4 - threadsNumber. The number of threads for data generation
 *
 * Example command line args:
 * "jdbc:postgresql://localhost:5432/mydatabase?user=user&password=password" "10" "30" "500" "20"
 */
public class Main {
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private static final Random RANDOM = new Random();

    private static int tablesNumber;
    private static int columnsNumber;
    private static int rowsNumber;
    private static int threadsNumber;
    private static DataSource dataSource;

    public static void main(String[] args) throws Exception {
        init(args);

        SqlUtil.dropAllTables(dataSource);

        long startTime = new Date().getTime();
        fillDatabase();
        long endTime = new Date().getTime();

        System.out.println("Total time: " + (endTime - startTime));
    }

    private static void init(String[] args) throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        String connectionUrl = args[0];
        tablesNumber = Integer.parseInt(args[1]);
        columnsNumber = Integer.parseInt(args[2]);
        rowsNumber = Integer.parseInt(args[3]);
        threadsNumber = Integer.parseInt(args[4]);
        dataSource = SqlUtil.setupDataSource(connectionUrl);
    }

    private static void fillDatabase() throws InterruptedException {

        GeneratorOptions options = new GeneratorOptions(dataSource, new ArrayBlockingQueue<String>(500));
        List<Thread> threads = startThreads(options);

        for (int i = 0; i < tablesNumber; i++) {
            List<Column> columns = createColumns();
            String tableName = "table_" + i + "_" + RANDOM.nextInt(Integer.MAX_VALUE);

            String createTableSql = SqlUtil.generateCreateTableSql(tableName, columns);
            SqlUtil.executeUpdate(createTableSql, dataSource);
            SqlUtil.generateInsertSqlToQueue(tableName, columns, rowsNumber, options.getSqlQueries());
        }

        options.setHasMoreQueries(false);
        awaitTermination(threads);
    }

    private static List<Column> createColumns() {
        List<Column> columns = new ArrayList<>(columnsNumber);
        for (int i = 0; i < columnsNumber; i++) {
            columns.add(Column.getRandom("column_" + i + "_"));
        }
        return columns;
    }

    private static List<Thread> startThreads(GeneratorOptions generatorOptions) {
        List<Thread> threads = new ArrayList<>(threadsNumber);
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new DataGenerator(generatorOptions));
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
}
