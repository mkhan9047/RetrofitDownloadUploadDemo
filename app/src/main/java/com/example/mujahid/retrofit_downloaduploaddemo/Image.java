package com.example.mujahid.retrofit_downloaduploaddemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujahid on 1/26/2018.
 */

public class Image {

    @SerializedName("title")
    private String Title;
    @SerializedName("image")
    private String Image;
    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }



}
