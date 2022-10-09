package com.lab6.datasource.dao;

import java.sql.Connection;

public abstract class BaseDao {

    protected Connection connection;
    protected final String tableName;
    public BaseDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }
}
