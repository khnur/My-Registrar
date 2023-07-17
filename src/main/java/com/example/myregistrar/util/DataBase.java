package com.example.myregistrar.util;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";

    private static DataSource dataSource;

    public static DataSource getDataSource() throws ClassNotFoundException {
        if (dataSource == null) {
            Class.forName(DRIVER_CLASS_NAME);
            dataSource = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
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
