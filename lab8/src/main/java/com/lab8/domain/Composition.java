package com.lab8.domain;

import javax.persistence.*;

@Entity
@Table(name = "composition")
@NamedQueries({
        @NamedQuery(name = "Composition.findByName",
                query = "SELECT a FROM Composition a WHERE a.name = :name"),
        @NamedQuery(name = "Composition.findAll",
                query = "SELECT a FROM Composition a left join fetch a.album")
})
public class Composition extends BaseEntity {

    public Composition() {
        super();
    }

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return String.format("Composition: \n name: %s \n duration: %d\n album: %s \n",
                getName(), duration, album.getName());
    }
}
