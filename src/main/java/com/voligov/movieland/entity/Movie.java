package com.voligov.movieland.entity;

import java.util.List;

public class Movie {

    private int id;
    private String name;
    private String nameOriginal;
    private int releaseYear;
    private String description;
    private double rating;
    private double price;
    private List<String> genres;
    private List<String> countries;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOriginal() {
        return nameOriginal;
    }

    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameOriginal='" + nameOriginal + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", genres='" + genres + '\'' +
                ", countries='" + countries + '\'' +
                '}';
    }
}
