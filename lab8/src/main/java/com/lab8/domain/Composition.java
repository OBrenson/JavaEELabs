package com.lab8.domain;

import javax.persistence.*;

@Entity
@Table(name = "composition")
public class Composition extends BaseEntity {


    @Column(name = "duration")
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    private Album album;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    private Composition(CompositionBuilder builder) {
        super(builder);
        this.album = builder.album;
        this.duration = builder.duration;
    }

    public static class CompositionBuilder extends BaseEntity.BaseBuilder {

        private Integer duration;

        private Album album;

        public CompositionBuilder(String name, Integer duration, Album album) {
            super(name);
            this.duration = duration;
            this.album = album;
        }

        @Override
        public Composition build() {
            return new Composition(this);
        }
    }
}
