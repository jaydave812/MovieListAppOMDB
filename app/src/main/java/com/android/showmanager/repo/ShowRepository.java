package com.android.showmanager.repo;

import java.util.List;

import com.android.showmanager.db.BookMarkDatabase;
import com.android.showmanager.api.ShowApiService;
import com.android.showmanager.api.ShowApi;
import com.android.showmanager.model.ShowDetails;
import com.android.showmanager.model.ShowSearchDetails;
import com.android.showmanager.model.SearchResponse;
import com.android.showmanager.utils.Constants;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * class to handle bookmark db
 */
public class ShowRepository
{
    private static final String TAG = ShowRepository.class.getSimpleName();
    private String DB_NAME = "showdb";
    private BookMarkDatabase bookMarkDatabase;
    LiveData<List<ShowSearchDetails>> showDetailList;
    private volatile static ShowRepository instance;
    private ShowApiService mService;


    private ShowRepository(Context application)
    {
        bookMarkDatabase = Room.databaseBuilder(application, BookMarkDatabase.class,
            DB_NAME).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        mService = ShowApiService.getInstance();
        showDetailList = getAllBookMark();
    }

    public static ShowRepository getInstance(Context application)
    {
        if (instance == null) {
            synchronized (ShowRepository.class) {
                if ((instance == null)) {
                    instance = new ShowRepository(application);
                }
            }

        }
        return instance;
    }

    /**
     * insert data in bookmark table
     */
    public boolean insertBookMark(ShowSearchDetails showSearchDetails)
    {
        boolean result = true;
        try {
            bookMarkDatabase.getDao().insertBookmark(showSearchDetails);
        }
        catch (Exception e) {
            result = false;
            Log.i(TAG, "Exception while inserting bookmark " + e);
        }
        return result;
    }

    /**
     * delete data from bookmark db
     */
    public boolean deleteBookMark(ShowSearchDetails showSearchDetails)
    {
        boolean result = true;
        try {
            bookMarkDatabase.getDao().deleteBookmark(showSearchDetails);
        }
        catch (Exception e) {
            result = false;
            Log.i(TAG, "Exception while inserting bookmark " + e);
        }
        return result;
    }


    /**
     * fetch all bookmarks from DB
     *
     * @return
     */
    public LiveData<List<ShowSearchDetails>> getAllBookMark()
    {
        return bookMarkDatabase.getDao().getAllBookMarks();
    }

    /**
     * Get show search result
     *
     * @param key  key to search
     * @param page page to get in result // by default 10 results are received in one call
     */
    public Call<SearchResponse> getSearchResult(String key, int page)
    {
        return mService.getApi().getSearchResults(key, page, Constants.API_KEY);
    }

    /**
     * Get details of a show
     *
     * @param imdbId id of show for which details needs to be found
     */
    public LiveData<ShowDetails> getShowDetails(String imdbId)
    {
        final MutableLiveData<ShowDetails> mutableLiveData = new MutableLiveData<>();
        ShowApi showApi = ShowApiService.getInstance().getApi();
        Call<ShowDetails> call = showApi.getShowDetails(imdbId, Constants.API_KEY);
        call.enqueue(new Callback<ShowDetails>()
        {
            @Override
            public void onResponse(Call<ShowDetails> call, Response<ShowDetails> response)
            {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ShowDetails> call, Throwable t)
            {
                mutableLiveData.setValue(null);

            }
        });
        return mutableLiveData;
    }


}
