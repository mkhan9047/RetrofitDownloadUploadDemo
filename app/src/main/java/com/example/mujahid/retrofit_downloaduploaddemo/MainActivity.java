package com.example.mujahid.retrofit_downloaduploaddemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button upload, chose;
    CircleImageView imageView;
    final static int imageRequest = 777;
    Bitmap bitmap;
    EditText title;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layout);
        upload = findViewById(R.id.uploadImg);
        chose = findViewById(R.id.chooseImg);
        imageView = findViewById(R.id.image);
        title = findViewById(R.id.title);
        chose.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

            switch (view.getId()){

               case R.id.uploadImg:
            uplaodImage();

                break;

               case R.id.chooseImg:
                   SelectImage();

                break;
            }
    }

    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,imageRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == imageRequest && resultCode == RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                layout.setVisibility(View.VISIBLE);
                chose.setEnabled(false);
                upload.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uplaodImage(){
        final String image = ImageToString();
        String imgTitle = title.getText().toString();
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Image> call = apiInterface.uploadImage(imgTitle,image);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d("test",response.body().toString());
                Image image1 = response.body();
                if (image1 != null) {
                    Toast.makeText(getApplicationContext(),image1.getResponse(),Toast.LENGTH_LONG).show();
                }
                layout.setVisibility(View.GONE);
                chose.setEnabled(true);
                upload.setEnabled(false);
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

    private String ImageToString(){
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,arrayOutputStream);
        byte[] array = arrayOutputStream.toByteArray();
        Log.d("data",Base64.encodeToString(array,Base64.DEFAULT));
        return Base64.encodeToString(array,Base64.DEFAULT);

    }
}
