package com.lab6.datasource.dao;

import com.lab6.datasource.Queries;
import com.lab6.datasource.domain.BaseEntity;
import com.lab6.datasource.domain.Composition;

import java.sql.Connection;
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
                composition.getName() + "," + composition.getDuration() + "," + composition.getAlbumId());
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
