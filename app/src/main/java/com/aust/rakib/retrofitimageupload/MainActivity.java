package com.aust.rakib.retrofitimageupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;


//https://pastebin.com/zjLnJHAD
public class MainActivity extends AppCompatActivity {

    private static String BASE_URL="http://10.0.3.2/RetrofitImageUpload/";  //10.0.3.2 fpr genymotion and 10.0.2.2 for pc
    private ImageView imageIV;
    private EditText  titleET;
    private static final int IMAGE_REQ=777;
    private Bitmap bitmap;
    Button upBT;
    Button selBT;
    Retrofit retrofit;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageIV= (ImageView) findViewById(R.id.image);
        titleET= (EditText) findViewById(R.id.titleET);
        upBT= (Button) findViewById(R.id.upBt);
        selBT= (Button) findViewById(R.id.selBt);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Uploading");

    }

    public void selectImage()                  //Will Select Image From Gallery
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQ);
    }

    public void uploadImage()
    {    progressDialog.show();
        String image2=imageToString();
        String title2=titleET.getText().toString();
        ////for HTTP log/////
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor =new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        ////for log/////

        if(BuildConfig.DEBUG)
        {
            builder.addInterceptor(httpLoggingInterceptor);  //only for debugging mode
        }

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build()) ////for log/////
                .build();
        apiInterface=retrofit.create(ApiInterface.class);

        Call<ImageResponse>imageResponseCall=apiInterface.uploadImage(title2,image2);
        imageResponseCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
               if(response.code()==200)
               {
                   Toast.makeText(MainActivity.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                   imageIV.setVisibility(View.GONE);
                   titleET.setVisibility(View.GONE);
                   selBT.setEnabled(true);
                   upBT.setEnabled(false);
                   progressDialog.dismiss();
               }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //To Get Result in same Activity
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQ && resultCode==RESULT_OK && data!=null)
        {
            Uri path=data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageIV.setImageBitmap(bitmap);
                imageIV.setVisibility(View.VISIBLE);
                titleET.setVisibility(View.VISIBLE);
                selBT.setEnabled(false);
                upBT.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public String imageToString(){  //Encoding Image here
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByte=byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(imageByte,Base64.DEFAULT);

    }

    public void UploadImage(View view) {
        uploadImage();
    }

    public void ChooseImage(View view) {
        selectImage();
    }
}
