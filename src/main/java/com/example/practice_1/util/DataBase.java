package com.example.practice_1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static DataSource dataSource;

    public static DataSource getDataSource() throws ClassNotFoundException {
        if (dataSource == null) {
            Class.forName(DRIVER_CLASS_NAME);
            dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }
        return dataSource;
    }

    public static void viewTable(Class<?> clazz) throws SQLException, ClassNotFoundException {
        String tableName = clazz.getSimpleName().toLowerCase();

        DatabaseMetaData metaData = getDataSource().getConnection().getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("TYPE_NAME");
            int columnSize = resultSet.getInt("COLUMN_SIZE");
            boolean nullable = resultSet.getBoolean("NULLABLE");

            System.out.println("Column Name: " + columnName);
            System.out.println("Column Type: " + columnType);
            System.out.println("Column Size: " + columnSize);
            System.out.println("Nullable: " + nullable);
            System.out.println();
        }
    }

    private DataBase() {
        throw new IllegalStateException("DataBase class created");
    }
}
