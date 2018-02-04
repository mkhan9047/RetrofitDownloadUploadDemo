package com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile;

import android.icu.text.AlphabeticIndex;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujahid on 1/26/2018.
 */

public class ApiClient {

    private static final String baseUrl = "http://10.0.2.2/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit == null){

            OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttp.addInterceptor(loggingInterceptor);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttp.build())
                    .build();
        }
        return retrofit;
    }
}
