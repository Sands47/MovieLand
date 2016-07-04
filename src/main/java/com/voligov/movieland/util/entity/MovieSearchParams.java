package com.voligov.movieland.util.entity;

public class MovieSearchParams {
    private String title;
    private String titleOriginal;
    private String releaseYear;
    private String country;
    private String genre;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isEmpty() {
        return (title == null) && (titleOriginal == null) && (releaseYear == null) && (country == null) && (genre == null);
    }

    @Override
    public String toString() {
        return "MovieSearchParams{" +
                "title='" + title + '\'' +
                ", titleOriginal='" + titleOriginal + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", country='" + country + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
