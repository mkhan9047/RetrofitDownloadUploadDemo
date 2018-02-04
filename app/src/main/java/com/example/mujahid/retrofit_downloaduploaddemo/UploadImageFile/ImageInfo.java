package com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile;



/**
 * Created by Mujahid on 1/26/2018.
 */

    public class ImageInfo {

    private String sender_name;

    private int sender_age;

    public ImageInfo(String sender_name, int sender_age) {
        this.sender_name = sender_name;
        this.sender_age = sender_age;
    }

    private String response;

    public String getResponse() {
        return response;
    }

}
