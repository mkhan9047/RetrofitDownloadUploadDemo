package com.example.mujahid.retrofit_downloaduploaddemo.UploadMultiPartFile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mujahid.retrofit_downloaduploaddemo.R;
import com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile.ApiClient;
import com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile.ApiInterface;
import com.example.mujahid.retrofit_downloaduploaddemo.UploadImageFile.ImageInfo;
import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiPartFileUplaod extends AppCompatActivity implements View.OnClickListener {
        private static final int Request = 779;
    Button upload, chose;
    CircleImageView imageView;
    EditText title, name, age;
    LinearLayout layout;
    Bitmap bitmap;
    ApiInterface apiInterface;
    String filePath;
    Uri path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_part_file_uplaod);
        upload = findViewById(R.id.uploadImg);
        chose = findViewById(R.id.chooseImg);
        layout = findViewById(R.id.layout);
        imageView = findViewById(R.id.image);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        chose.setOnClickListener(this);
        upload.setOnClickListener(this);

    }
    public String getPath(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj,
                null, null, null);
        assert cursor != null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Request);
    }

    protected void onActivityResult(int request, int resultCode, Intent data){
        super.onActivityResult(request, resultCode, data);
        if(request == Request && resultCode == RESULT_OK && data != null){
             path = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            filePath = getPath(path);

            if (path != null) {
                Log.d("Uri path:",path.toString());
            }else{
                Log.d("null here","path is null");
            }

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                upload.setEnabled(true);
                chose.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void UploadFile(Uri filePath, ImageInfo info){
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        File a = FileUtils.getFile(this,filePath);

     //   Log.d("mainPath",filePath.getPath());
        final RequestBody requestFile = RequestBody.create(MediaType.parse("image"),a);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",a.getName(),requestFile);
        Gson go = new Gson();
        String user = go.toJson(info);
        RequestBody description = RequestBody.create(MultipartBody.FORM,user);

        Call<ImageInfo> imageInfoCall = apiInterface.uploadMuliImage(description, body);
        imageInfoCall.enqueue(new Callback<ImageInfo>() {
            @Override
            public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {

                    ImageInfo info = response.body();
                if (info != null) {
                    Log.d("response",info.getResponse());
                }
            }

            @Override
            public void onFailure(Call<ImageInfo> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chooseImg:
                SelectImage();
                break;
            case R.id.uploadImg:
                ImageInfo info = new ImageInfo(name.getText().toString(), Integer.parseInt(age.getText().toString()));
         UploadFile(path, info);
                break;
        }
    }



}
