package com.lab6.datasource.domain;

public class Album extends BaseEntity{
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
