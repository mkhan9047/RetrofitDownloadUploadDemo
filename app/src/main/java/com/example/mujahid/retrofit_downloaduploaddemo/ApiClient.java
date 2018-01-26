package com.example.mujahid.retrofit_downloaduploaddemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujahid on 1/26/2018.
 */

public class ApiClient {

    private static final String baseUrl = "http://10.0.2.2/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
