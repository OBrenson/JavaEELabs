package com.lab8;

import com.lab8.dao.DaoService;
import com.lab8.domain.Album;
import com.lab8.domain.BaseEntity;
import com.lab8.domain.Composition;
import com.lab8.domain.Singer;

import java.util.*;
import java.util.stream.IntStream;

public abstract class DataUtil {

    private static Set<String> singersNames = new HashSet<>(Arrays.asList("Led Zeppelin", "Pink Floyd", "Black Sabbath",
            "The Doors", "AC/DC"));

    private static List<String> albumsNames = Arrays.asList("Led Zeppelin III", "The Wall", "War Pigs",
            "Strange Days", "Back In Black");

    private static List<String> compositionsNames = Arrays.asList("Immigrant Song", "Young Lust", "War Pigs ",
            "People Are Strange", "Hell Bells");

    public static void insertData(DaoService daoService) {
        List<BaseEntity> list = new ArrayList<>();
        singersNames.stream().forEachOrdered(n -> {
            Singer s = new Singer.SingerBuilder(n).build();
            daoService.save(s);
            list.add(s);
        });

        IntStream.range(0, albumsNames.size()).forEachOrdered(i -> {
            Album a = new Album.AlbumBuilder(albumsNames.get(i), "rock", (Singer) list.get(i)).build();
            daoService.save(a);
            list.add(a);
        });

        Random random = new Random();
        IntStream.range(0, compositionsNames.size()).forEachOrdered(i -> {
            int duration = random.ints(100, 400).findFirst().getAsInt();
            Composition c = new Composition.CompositionBuilder(
                    compositionsNames.get(i), duration, (Album) list.get(i + 5)
            ).build();
            daoService.save(c);
        });
    }
}
