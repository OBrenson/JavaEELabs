package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.Album;
import com.lab6.datasource.domain.BaseEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AlbumDAO extends BaseDao implements Dao{

    public AlbumDAO(Connection connection) {
        super(connection, "album");
    }


    @Override
    public void create(BaseEntity entity) throws SQLException {
        Album album = (Album) entity;
        Statement statement = connection.createStatement();
        String query = String.format(Queries.CREATE, tableName, "name, genre",
                String.format(Queries.VARCHAR,album.getName()) + "," + String.format(Queries.VARCHAR,album.getGenre()));
        statement.execute(query);
    }

    @Override
    public void update(String name, BaseEntity entity) throws SQLException {
        String temp = String.format("name = '%s', genre = '%s'",
                entity.getName(),
                ((Album)entity).getGenre());
        update(temp, name);
    }

    @Override
    public BaseEntity get(String name) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format(Queries.GET, tableName, name);
        ResultSet rs = statement.executeQuery(query);
        Album res = new Album();
        while (rs.next()) {
            res.setName(rs.getString(1));
            res.setId(rs.getLong(2));
            res.setGenre(rs.getString(3));
        }
        return res;
    }
}
