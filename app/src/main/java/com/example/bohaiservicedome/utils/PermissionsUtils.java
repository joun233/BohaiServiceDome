package com.example.bohaiservicedome.utils;


import android.content.Context;
import android.net.ConnectivityManager;


public class PermissionsUtils {
    private static final String TAG = PermissionsUtils.class.getSimpleName();

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo()!=null){
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

}
