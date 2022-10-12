package com.lab6;

import com.lab6.datasource.DatasourceService;
import com.lab6.datasource.domain.Album;
import com.lab6.datasource.domain.BaseEntity;
import com.lab6.datasource.domain.Composition;
import com.lab6.datasource.domain.Singer;

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
        test(new Singer(), new Singer(), ds);
        test(new Album(), new Album(), ds);
        testComposition(ds);
        ds.connectSignerAndAlbum("The Doors", "War Pigs");
        ds.deleteConnectionSingerAndAlbum("The Doors", "War Pigs");
    }

    private static void testComposition(DatasourceService ds) throws SQLException {
        Composition composition = new Composition();
        composition.setAlbumId(10L);
        composition.setDuration(10);
        test(composition, new Composition(), ds);
    }

    private static void test(BaseEntity baseEntity,BaseEntity baseEntity1, DatasourceService ds) throws SQLException {
        baseEntity.setName("asd");
        ds.create(baseEntity);
        ds.get(baseEntity1, "asd");
        assert baseEntity1.getName().equals(baseEntity.getName());

        baseEntity.setName("bsd");
        ds.update("asd", baseEntity);
        ds.get(baseEntity1, "bsd");
        assert baseEntity1.getName().equals("bsd");

        ds.delete(baseEntity);
        ds.get(baseEntity1, "asd");
        assert baseEntity1.getName() == null;
    }
}
