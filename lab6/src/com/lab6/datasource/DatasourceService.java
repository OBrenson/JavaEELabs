package com.lab6.datasource;


import com.lab6.datasource.dao.*;
import com.lab6.datasource.domain.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatasourceService {

    private Connection connection;
    private final Map<Class, Dao> daoMap = new HashMap<>();

    public DatasourceService() throws SQLException, IOException {
        connection = createConnection();
        daoMap.put(Singer.class, new SignerDao(connection));
        daoMap.put(Composition.class, new CompositionDao(connection));
        daoMap.put(Album.class, new AlbumDAO(connection));
    }

    public Map<String, Long> getShortestInAlbum() throws SQLException {
        Map<String, Long> res = new HashMap<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(Queries.SHORT_ALBUM);
        while (rs.next()) {
            res.put(rs.getString("name"), rs.getLong("dur"));
        }
        return res;
    }

    public void connectSignerAndAlbum(String singerName, String albumName) throws SQLException {
        String query = String.format(Queries.CONNECT, singerName, albumName);
        Statement statement = connection.createStatement();
        statement.execute(query);
    }

    public void deleteConnectionSingerAndAlbum(String singerName, String albumName) throws SQLException {
        String query = String.format(Queries.DELETE_CONNECT, singerName, albumName);
        Statement statement = connection.createStatement();
        statement.execute(query);
    }

    public void create(BaseEntity entity) throws SQLException {
        getDao(entity).create(entity);
    }

    public void update(String name, BaseEntity entity) throws SQLException {
        getDao(entity).update(name, entity);
    }

    public void delete(BaseEntity entity) throws SQLException {
        getDao(entity).delete(entity);
    }

    public void get(BaseEntity entity, String name) throws SQLException {
        BaseEntity be = getDao(entity).get(name);
        entity.setId(be.getId());
        entity.setName(be.getName());
        if (entity instanceof Composition) {
            ((Composition) entity).setDuration(((Composition)be).getDuration());
            ((Composition) entity).setAlbumId(((Composition)be).getAlbumId());
        }
        if (entity instanceof Album) {
            ((Album) entity).setGenre(((Album)be).getGenre());
        }
    }

    private Dao getDao(BaseEntity entity) {
        if (entity instanceof Singer) {
            return daoMap.get(Singer.class);
        }
        if (entity instanceof Composition) {
            return daoMap.get(Composition.class);
        }
        if (entity instanceof Album) {
            return daoMap.get(Album.class);
        }
        return null;
    }

    private Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("/Users/19722883-mobile/Desktop/labs/JavaEELabs/lab6/resources/database.properties");
        properties.load(fis);
        fis.close();
        String drivers = properties.getProperty("jdbc.drivers");
        System.setProperty("jdbc.drivers", drivers);
        String url = properties.getProperty("jdbc.url");
        String pass = properties.getProperty("jdbc.password");
        String user = properties.getProperty("jdbc.username");
        return DriverManager.getConnection(url, user, pass);
    }
}
