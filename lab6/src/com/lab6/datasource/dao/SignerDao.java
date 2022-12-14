package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;
import com.lab6.datasource.domain.Composition;
import com.lab6.datasource.domain.Singer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignerDao extends BaseDao implements Dao {

    public SignerDao(Connection connection) {
        super(connection, "signer");
    }

    @Override
    public void create(BaseEntity entity) throws SQLException {
        Singer singer = (Singer) entity;
        Statement statement = connection.createStatement();
        String query = String.format(Queries.CREATE, tableName, "name", String.format(Queries.VARCHAR,singer.getName()));
        statement.execute(query);
    }

    @Override
    public void update(String name, BaseEntity entity) throws SQLException {
        String temp = String.format("name = '%s'", entity.getName());
        update(temp, name);
    }

    @Override
    public BaseEntity get(String name) throws SQLException {
        Statement statement = connection.createStatement();
        Singer singer = new Singer();
        String query = String.format(Queries.GET, tableName, name);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            singer.setName(rs.getString(1));
            singer.setId(rs.getLong(2));
        }
        return singer;
    }
}
