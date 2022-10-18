package com.lab8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "album")
@NamedQueries({
        @NamedQuery(name = "Album.findByname",
                query = "SELECT a FROM Album a WHERE a.name = :name "),
        @NamedQuery(name = "Album.findAll",
                query = "SELECT a FROM Album a left join fetch a.singer"),
        @NamedQuery(name = "Album.findByid",
                query = "SELECT a FROM Album a WHERE a.id = :id")
})
@AttributeOverride(name = "name", column = @Column(name = "name", unique = true))
public class Album extends BaseEntity {

    public Album() {
        super();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singer_id", nullable = false)
    private Singer singer;

    @OneToMany(mappedBy = "album", cascade = CascadeType.REMOVE)
    private Set<Composition> compositions;

    @Column(name = "genre")
    private String genre;

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(Set<Composition> compositions) {
        this.compositions = compositions;
    }

    private Album(AlbumBuilder builder) {
        super(builder);
        this.genre = builder.genre;
        this.compositions = builder.compositions;
        this.singer = builder.singer;
    }

    public static class AlbumBuilder extends BaseEntity.BaseBuilder {

        private String genre;

        private Singer singer;

        private Set<Composition> compositions;

        public AlbumBuilder(String name, String genre, Singer singer) {
            super(name);
            this.genre = genre;
            this.singer = singer;
        }

        public AlbumBuilder addComposition(Composition composition) {
            if (this.compositions == null) {
                this.compositions = new HashSet<>();
            }
            this.compositions.add(composition);
            return this;
        }

        @Override
        public Album build() {
            return new Album(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Album: \n name: %s \n singer: %s\n genre:%s\n", getName(), singer.getName(), genre);
    }
}
