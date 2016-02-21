package com.ruslan.mentoring.SQL.task02;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

public class SqlUtil {
    public static void executeSql(String sql, DataSource dataSource) {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropAllTables(DataSource dataSource) {
        SqlUtil.executeSql("DROP SCHEMA public CASCADE; CREATE SCHEMA public;", dataSource);
    }

    public static String generateCreateTableSql(String tableName, List<Column> columns) {

        StringBuilder createTableSql = new StringBuilder("CREATE TABLE ").append(tableName).append("(");
        for (int i = 0; i < columns.size(); i++) {
            createTableSql.append(columns.get(i).toString());
            if (i < columns.size() - 1) {
                createTableSql.append(",");
            }
        }
        createTableSql.append(");");

        return createTableSql.toString();
    }

    public static void generateInsertSqlToQueue(String tableName, List<Column> columns, int rowsNumber,
                                                BlockingQueue<String> queue)
            throws InterruptedException {

        for (int i = 0; i < rowsNumber; i++) {
            StringBuilder insertSql = new StringBuilder("INSERT INTO ").append(tableName).append(" VALUES (");
            for (Column column: columns) {
                insertSql.append("'").append(column.getType().getRandomValue()).append("'").append(",");
            }
            insertSql.deleteCharAt(insertSql.length() - 1);
            insertSql.append(");");
            queue.put(insertSql.toString());
        }
    }

    public static DataSource setupDataSource(String connectionUrl) {
        // No SSL validation
        Properties properties = new Properties() {{
            put("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        }};
        //
        // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string
        //
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(connectionUrl, properties);

        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory, new GenericObjectPoolConfig(){{
                    setMaxTotal(10);// 80 - 141475; 30 - 70998; 10 - 57196
                }});

        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        return new PoolingDataSource<>(connectionPool);
    }
}
