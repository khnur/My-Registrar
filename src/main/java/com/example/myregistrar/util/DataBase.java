package com.example.myregistrar.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

public class DataBase {
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";

    private final DataSource dataSource;

    public DataBase(DataSource dataSource) throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
