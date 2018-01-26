package com.example.mujahid.retrofit_downloaduploaddemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Mujahid on 1/26/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("upload.php")
    Call<Image> uploadImage(@Field("title") String title, @Field("image") String image);

}
