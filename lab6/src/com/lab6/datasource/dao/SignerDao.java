package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;
import com.lab6.datasource.domain.Singer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SignerDao implements Dao {

    private static final String tableName = "singer";
    private Connection connection;

    public SignerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(BaseEntity entity) throws SQLException {
        Singer singer = (Singer) entity;
        Statement statement = connection.createStatement();
        String query = String.format(Queries.CREATE, tableName, "name", singer.getName());
        statement.execute(query);
    }

    @Override
    public void update(Long id, BaseEntity entity) {

    }

    @Override
    public void delete(BaseEntity entity) {

    }

    @Override
    public void get(BaseEntity entity) {

    }
}
