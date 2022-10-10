package com.lab6.datasource;

public class Queries {
    public static final String SHORT_ALBUM =
            "select  a.name as name, MIN(c.duration) as dur from album a join composition c on a.id = c.album_id where c.duration > 5 group by a.name ";

    public static final String CREATE = "INSERT INTO %s(id, %s) VALUES (nextval('id_seq'), %s)";

    public static final String GET = "SELECT * FROM %s WHERE name = '%s'";

    public static final String VARCHAR = "'%s'";

    public static final String DELETE = "DELETE FROM %s WHERE name = ?";

    public static final String UPDATE = "UPDATE %s SET %s WHERE name = '%s'";

    public static final String CONNECT = "INSERT INTO signer_album(signer_id, album_id) (select s.id, a.id from album a join signer s on a.id <> s.id where s.name = '%s' and a.name = '%s' )";

    public static final String DELETE_CONNECT = "DELETE FROM signer_album where (signer_id, album_id) = (select s.id, a.id from album a join signer s on a.id <> s.id where s.name = '%s' and a.name = '%s' )";

}


