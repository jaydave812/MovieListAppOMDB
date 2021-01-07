package com.android.showmanager.model;

import com.google.gson.annotations.SerializedName;

public class ShowDetails
{
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("Genre")
    private String genre;
    @SerializedName("Director")
    private String director;
    @SerializedName("imdbRating")
    private String imdbRating;
    @SerializedName("Actors")
    private String actors;
    @SerializedName("Poster")
    private String poster;

    public ShowDetails(String title, String year, String genre, String director, String imdbRating, String actors,
        String poster)
    {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.imdbRating = imdbRating;
        this.actors = actors;
        this.poster = poster;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public String getImdbRating()
    {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating)
    {
        this.imdbRating = imdbRating;
    }

    public String getActors()
    {
        return actors;
    }

    public void setActors(String actors)
    {
        this.actors = actors;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }
}
