package com.android.showmanager.adapter;

import com.android.showmanager.model.ShowSearchDetails;

public interface IShowClickListner
{
    void onShowClick(ShowSearchDetails showDetails);
    void onSaveBookMark(ShowSearchDetails showDetails);
    void onDeleteBookMark(ShowSearchDetails showDetails);
}
