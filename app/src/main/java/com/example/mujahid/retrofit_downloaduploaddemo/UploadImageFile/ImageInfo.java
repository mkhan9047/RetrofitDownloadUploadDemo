package com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile;



/**
 * Created by Mujahid on 1/26/2018.
 */

    public class ImageInfo {

    private String sender_name;

    private int sender_age;

    private String email;

    private String phone;

    public ImageInfo(String sender_name, int sender_age, String email, String phone) {
        this.sender_name = sender_name;
        this.sender_age = sender_age;
        this.email = email;
        this.phone = phone;
    }


    private String response;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getResponse() {
        return response;
    }

}
