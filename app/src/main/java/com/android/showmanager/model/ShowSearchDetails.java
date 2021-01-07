package com.android.showmanager.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarkData", indices = {@Index(value = "imdbID", unique = true)})
public class ShowSearchDetails
{

    @PrimaryKey(autoGenerate = true)
    private int _id;
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String imdbID;
    @Ignore
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;
    @Ignore
    @SerializedName("totalResults")
    private String totalResults;

    @Ignore
    public ShowSearchDetails()
    {
    }

    public ShowSearchDetails(int _id, String title, String year, String imdbID, String poster)
    {
        this._id = _id;
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
    }

    public ShowSearchDetails(String title, String year, String imdbID, String type, String poster, String totalResults)
    {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
        this.totalResults = totalResults;
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

    public String getImdbID()
    {
        return imdbID;
    }

    public void setImdbID(String imdbID)
    {
        this.imdbID = imdbID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    public String getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(String totalResults)
    {
        this.totalResults = totalResults;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }
}
