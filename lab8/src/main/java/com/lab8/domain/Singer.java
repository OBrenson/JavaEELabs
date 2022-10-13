package com.lab8.domain;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "singer")
@AttributeOverride(name = "name", column = @Column(name = "name", unique = true))
public class Singer extends BaseEntity {



    @OneToMany(mappedBy = "singer")
    Set<Album> albums;

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    private Singer(SingerBuilder builder) {
        super(builder);
        this.albums = builder.albums;
    }

    public static class SingerBuilder extends BaseEntity.BaseBuilder {

        private Set<Album> albums;

        public SingerBuilder(String name) {
            super(name);
        }

        public SingerBuilder addAlbum(Album album) {
            if (this.albums == null) {
                this.albums = new HashSet<>();
            }
            albums.add(album);
            return this;
        }

        @Override
        public Singer build() {
            return new Singer(this);
        }
    }
}
