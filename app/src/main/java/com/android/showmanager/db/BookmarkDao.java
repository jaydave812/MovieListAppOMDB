package com.android.showmanager.db;

import java.util.List;

import com.android.showmanager.model.ShowSearchDetails;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookmarkDao
{
    @Insert
    void insertBookmark(ShowSearchDetails bookMark);

    @Query("SELECT * FROM bookmarkdata order by _id desc")
    LiveData<List<ShowSearchDetails>> getAllBookMarks();

    @Delete
    void deleteBookmark(ShowSearchDetails bookMark);
}
