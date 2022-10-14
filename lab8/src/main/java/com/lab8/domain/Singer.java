package com.lab8.domain;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "singer")
@AttributeOverride(name = "name", column = @Column(name = "name", unique = true))
@NamedQueries({
        @NamedQuery(name = "Singer.findByName",
                query = "SELECT a FROM Singer a WHERE a.name = :name"),
        @NamedQuery(name = "Singer.findAll",
                query = "SELECT a FROM Singer a")
})
public class Singer extends BaseEntity {

    public Singer() {
        super();
    }

    @OneToMany(mappedBy = "singer", cascade = CascadeType.REMOVE)
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

    @Override
    public String toString() {
        return String.format("Singer: \n name: %s\n", getName());
    }
}
