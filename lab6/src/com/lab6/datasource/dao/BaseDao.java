package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String query = String.format(Queries.DELETE, tableName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getName());
        statement.executeUpdate();
    }

    protected void update(String temp, String name) throws SQLException {
        String query = String.format(Queries.UPDATE, tableName, temp, name);
        Statement statement = connection.createStatement();
        statement.execute(query);
    }
}
