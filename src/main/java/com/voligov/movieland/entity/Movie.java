package com.voligov.movieland.entity;

import java.util.List;

public class Movie {

    private Integer id;
    private String name;
    private String nameOriginal;
    private Integer releaseYear;
    private String description;
    private Double rating;
    private Double price;
    private String genreIds;
    private List<Genre> genres;
    private List<String> countries;
    private List<Review> reviews;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
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
