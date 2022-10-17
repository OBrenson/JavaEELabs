package com.lab8.domain;

import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "composition")
@NamedQueries({
        @NamedQuery(name = "Composition.findByname",
                query = "SELECT a FROM Composition a WHERE a.name = :name"),
        @NamedQuery(name = "Composition.findAll",
                query = "SELECT a FROM Composition a left join fetch a.album"),
        @NamedQuery(name = "Composition.findByid",
                query = "SELECT a FROM Composition a WHERE a.id = :id")
})
@Check(constraints = "duration > 0")
public class Composition extends BaseEntity {

    public Composition() {
        super();
    }

    @Column(name = "duration", nullable = false)
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
