package com.android.showmanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils
{

    /**
     * This method take the application context and check on the internet connectivity.
     * if the device connected with the internet! the method will return true, else it will return false.
     *
     * @param context  context.
     * @return boolean true if internet connected, false if it not connected.
     */
    public static boolean checkInternetConnection(Context context)
    {
        ConnectivityManager connectivityManager =

            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
