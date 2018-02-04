package com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Mujahid on 1/26/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageInfo> uploadImage(@Field("title") String title, @Field("image") String image);

    @Multipart
    @POST("uploadNext.php")
    Call<ImageInfo> uploadMuliImage(
            @Part("sender_information") RequestBody description,
            @Part MultipartBody.Part file
    );

}
