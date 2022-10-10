package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDao {

    protected Connection connection;
    protected final String tableName;
    public BaseDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    public void delete(BaseEntity entity) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format(Queries.DELETE, tableName, entity.getName());
        statement.execute(query);
    }

    protected void update(String temp, String name) throws SQLException {
        String query = String.format(Queries.UPDATE, tableName, temp, name);
        Statement statement = connection.createStatement();
        statement.execute(query);
    }
}
