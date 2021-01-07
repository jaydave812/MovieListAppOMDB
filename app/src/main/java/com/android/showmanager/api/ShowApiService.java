package com.android.showmanager.api;

import com.android.showmanager.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowApiService
{
    private Retrofit retrofit;
    private volatile static ShowApiService instance;

    private ShowApiService()
    {
        initializeRetrofit();
    }

    public static ShowApiService getInstance()
    {
        if (instance == null) {
            synchronized (ShowApiService.class) {
                if (instance == null) {
                    instance = new ShowApiService();
                }
            }

        }
        return instance;
    }

    /**
     * return retrofit instance
     *
     * @return
     */
    private void initializeRetrofit()
    {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DATA_REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

    }


    /**
     * return api reference
     * @return
     */
    public ShowApi getApi(){
        return  retrofit.create(ShowApi.class);
    }
}
