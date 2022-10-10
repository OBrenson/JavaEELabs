INSERT INTO signer (id, name) VALUES
    (nextval('id_seq'), 'Led Zeppelin'),
    (nextval('id_seq'), 'Pink Floyd'),
    (nextval('id_seq'), 'Black Sabbath'),
    (nextval('id_seq'), 'The Doors'),
    (nextval('id_seq'), 'AC/DC');

INSERT INTO composition  (id, name, album_id, duration) VALUES
    (nextval('id_seq'), 'Immigrant Song', 7, 100),
    (nextval('id_seq'), 'Young Lust', 8, 200),
    (nextval('id_seq'), 'War Pigs', 9, 300),
    (nextval('id_seq'), 'Peaople Are Strange', 10, 250),
    (nextval('id_seq'), 'Hell Bells', 11, 132);

INSERT INTO album  (id, name, genre) VALUES
    (nextval('id_seq'), 'Led Zeppelin III', 'heavy metal'),
    (nextval('id_seq'), 'The Wall', 'progressive rock'),
    (nextval('id_seq'), 'War Pigs', 'hard rock'),
    (nextval('id_seq'), 'Strange Days', 'progressive rock'),
    (nextval('id_seq'), 'Back In Black', 'hard rock');

INSERT INTO signer_album  (signer_id, album_id) VALUES
    (2,7),
    (3,8),
    (4,9),
    (5,10),
    (6,11);


       INSERT INTO composition  (id, name, album_id, duration) VALUES
        (nextval('id_seq'), 'Mother', 8, 100),
      (nextval('id_seq'), 'The Thin Ice', 8, 400),
     (nextval('id_seq'), 'The Happiest Days of Our Lives', 8, 500),
    (nextval('id_seq'), 'One of My Turns', 8, 100);

       INSERT INTO composition  (id, name, album_id, duration) VALUES
        (nextval('id_seq'), 'Paranoid', 9, 100),
      (nextval('id_seq'), 'Planet Caravan', 9, 400),
     (nextval('id_seq'), 'Rat Salad', 9, 500),
    (nextval('id_seq'), 'Fairies Wear Boots', 9, 100);