package com.lab6.datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatasourceService {

    private Connection connection;

    public DatasourceService() throws SQLException, IOException {
        connection = createConnection();
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

    private Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\PC\\Desktop\\labs\\JavaEELabs\\lab6\\resources\\database.properties");
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
