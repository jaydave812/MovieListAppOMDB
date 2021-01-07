package com.android.showmanager.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchResponse
{
    @SerializedName("Search")
    List<ShowSearchDetails> showDetailsList;
    @SerializedName("totalResults")
    String totalResult;

    public SearchResponse(List<ShowSearchDetails> showDetailsList, String totalResult)
    {
        this.showDetailsList = showDetailsList;
        this.totalResult = totalResult;
    }

    public List<ShowSearchDetails> getShowDetailsList()
    {
        return showDetailsList;
    }

    public void setShowDetailsList(List<ShowSearchDetails> showDetailsList)
    {
        this.showDetailsList = showDetailsList;
    }

    public String getTotalResult()
    {
        return totalResult;
    }

    public void setTotalResult(String totalResult)
    {
        this.totalResult = totalResult;
    }
}
