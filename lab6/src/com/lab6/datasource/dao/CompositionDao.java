package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;
import com.lab6.datasource.domain.Composition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompositionDao extends BaseDao implements Dao{

    public CompositionDao(Connection connection) {
        super(connection, "composition");
    }

    @Override
    public void create(BaseEntity entity) throws SQLException {
        Composition composition = (Composition) entity;
        Statement statement = connection.createStatement();
        String query = String.format(Queries.CREATE, tableName, "name,duration,album_id",
                String.format(Queries.VARCHAR,composition.getName()) + "," +
                        composition.getDuration() + "," + composition.getAlbumId());
        statement.execute(query);
    }

    @Override
    public void update(String name, BaseEntity entity) throws SQLException {
        String temp = String.format("name = '%s',duration = %d, album_id = %d",
                entity.getName(),
                ((Composition)entity).getDuration(),
                ((Composition)entity).getAlbumId());
        update(temp, name);
    }

    @Override
    public void delete(BaseEntity entity) {

    }

    @Override
    public BaseEntity get(String name) throws SQLException {
        Statement statement = connection.createStatement();
        Composition composition = new Composition();
        String query = String.format(Queries.GET, tableName, name);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            composition.setName(rs.getString(1));
            composition.setId(rs.getLong(2));
            composition.setDuration(rs.getInt(3));
            composition.setAlbumId(rs.getLong(4));
        }
        return composition;
    }
}
