package com.lab6;

import com.lab6.datasource.DatasourceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DatasourceService ds = new DatasourceService();
        Map<String, Long> shortest = ds.getShortestInAlbum();
        for (Map.Entry<String, Long> entry : shortest.entrySet()) {
            System.out.println("Album: " + entry.getKey() + ", Duration: " + entry.getValue().toString());
        }
    }

}
