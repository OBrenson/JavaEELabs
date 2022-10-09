package com.lab6.datasource;

public class Queries {
    public static final String SHORT_ALBUM =
            "select  a.name as name, MIN(c.duration) as dur from album a join composition c on a.id = c.album_id where c.duration > 5 group by a.name ";

    public static final String CREATE = "INSERT INTO %s(id, %s) VALUES (nextval('id_seq'), %s)";
}


