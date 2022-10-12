create table signer (name varchar(30) not null, id bigint primary key);
create table album (name varchar(30) not null, id bigint primary key, genre varchar(30) not null);
create table composition (name varchar(30) not null, id bigint primary key, duration integer CONSTRAINT positive_duration CHECK (duration > 0) );



alter table composition add column album_id bigint not null;
alter table composition add CONSTRAINT fk_album
      FOREIGN KEY(album_id)
	  REFERENCES album(id)

create table signer_album (signer_id bigint not null, album_id bigint not null,
	CONSTRAINT fk_signer
    FOREIGN KEY(signer_id)
	REFERENCES signer(id)
	ON DELETE CASCADE,
	CONSTRAINT album_fk
    FOREIGN KEY(album_id)
	REFERENCES album(id)
	ON DELETE CASCADE,
	PRIMARY KEY(signer_id, album_id));



create sequence id_seq
    start 1
    increment 1
    NO MAXVALUE
    CACHE 1;