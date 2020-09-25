package com.example.bohaiservicedome.utils;

import android.content.Context;

import com.example.bohaiservicedome.config.ApiConfig;
import com.example.bohaiservicedome.config.UrlsConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/6 12
 */
public class HttpUtils {
    public static final String TAG = "HttpUtils";

    public static void getEnglishSentence(Context context, String date , StringCallback callback) throws IOException {
        OkGo.<String>get(UrlsConfig.EVERYDAY_ENGLISH_API)
                .tag(context)
                .params("key", ApiConfig.API_AppKey)
                .params("date", date)
                .execute(callback);
    }


}
