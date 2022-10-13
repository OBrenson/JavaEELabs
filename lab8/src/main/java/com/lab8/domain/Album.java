package com.lab8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "album")
public class Album extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "singer_id")
    private Singer singer;

    @OneToMany(mappedBy = "album")
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
}
