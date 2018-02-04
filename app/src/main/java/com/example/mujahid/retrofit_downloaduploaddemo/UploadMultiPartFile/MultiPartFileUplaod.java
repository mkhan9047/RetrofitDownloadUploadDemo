package com.example.mujahid.retrofit_downloaduploaddemo.UploadMultiPartFile;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    ArrayList<Uri> list;
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


    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        }
        startActivityForResult(intent, Request);
    }

    protected void onActivityResult(int request, int resultCode, Intent data){
        super.onActivityResult(request, resultCode, data);
        if(request == Request && resultCode == RESULT_OK && data != null){
            path = data.getData();
            if(path!=null){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    imageView.setImageBitmap(bitmap);
                    upload.setEnabled(true);
                    chose.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                ClipData clip = data.getClipData();
                list = new ArrayList<>();

                if (clip != null) {
                    for (int i = 0; i < clip.getItemCount(); i++) {
                        ClipData.Item item = clip.getItemAt(i);
                        list.add(item.getUri());
                    }
                    upload.setEnabled(true);
                    chose.setEnabled(false);
                }

                for (Uri uri : list) {
                    Log.d("path", uri.getPath());
                }

            }
            /*

             path = data.getData();

*/
        }

    }

    private void UploadFile(Uri filePath, ImageInfo info){
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        File a = FileUtils.getFile(this,filePath);
        List<MultipartBody.Part> partList = new ArrayList<>();
     //   Log.d("mainPath",filePath.getPath());
       // final RequestBody requestFile = RequestBody.create(MediaType.parse("image"),a);
       // MultipartBody.Part body = MultipartBody.Part.createFormData("file",a.getName(),requestFile);
        for(int i = 0; i < list.size(); i ++){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image"),FileUtils.getFile(this,list.get(i)));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",FileUtils.getFile(this,list.get(i)).getName(),requestBody);
            partList.add(part);
        }

        Gson go = new Gson();
        String user = go.toJson(info);
        RequestBody description = RequestBody.create(MultipartBody.FORM,user);
        RequestBody email = RequestBody.create(MultipartBody.FORM,info.getEmail());
        RequestBody phone = RequestBody.create(MultipartBody.FORM,info.getPhone());
        HashMap<String, RequestBody> map = new HashMap<>();
            map.put("email",email);
            map.put("phone",phone);

        Call<ImageInfo> imageInfoCall = apiInterface.uploadMuliArrImage(description, map, partList);
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
                ImageInfo info = new ImageInfo(name.getText().toString(), Integer.parseInt(age.getText().toString()),"mkhan9047@gmaill.com","01955784397");
         UploadFile(path, info);
                break;
        }
    }



}
