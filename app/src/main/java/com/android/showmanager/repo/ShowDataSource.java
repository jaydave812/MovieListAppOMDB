package com.android.showmanager.repo;

import com.android.showmanager.model.SearchResponse;
import com.android.showmanager.model.ShowSearchDetails;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class Acts as data source
 */
public class ShowDataSource extends androidx.paging.PageKeyedDataSource<Integer, ShowSearchDetails>
{
    private static final String TAG = ShowDataSource.class.getSimpleName();
    //we will start from the first page which is 1
    private static int FIRST_PAGE = 1;

    private String mSearchKey;
    private Context mContext;
    private ShowRepository mRepository;

    public ShowDataSource(String searchKey, Context context)
    {
        this.mSearchKey = searchKey;
        this.mContext = context;
        mRepository = ShowRepository.getInstance(mContext);
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
        @NonNull final LoadInitialCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load initial " +mSearchKey +" first page " + FIRST_PAGE);

        Call<SearchResponse> call = mRepository.getSearchResult(mSearchKey, FIRST_PAGE);
        call.enqueue(new Callback<SearchResponse>()
        {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response)
            {
                Log.i(TAG, "Initial load completed");
                if(response == null || response.body()==null) return;
                //Log.i(TAG, "response size is " + response.body().getShowDetailsList());
                FIRST_PAGE++;
                callback.onResult(response.body().getShowDetailsList(), null, FIRST_PAGE);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());
            }
        });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
        @NonNull LoadCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load before");

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
        @NonNull final LoadCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load After " +mSearchKey +" page "+ params.key);
        Call<SearchResponse> call = mRepository.getSearchResult(mSearchKey, params.key);
        call.enqueue(new Callback<SearchResponse>()
        {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response)
            {
                Log.i(TAG, "After load completed");
                if(response == null || response.body()==null) return;
                callback.onResult(response.body().getShowDetailsList(), params.key + 1);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());

            }
        });

    }
}
