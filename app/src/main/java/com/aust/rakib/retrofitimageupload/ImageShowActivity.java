package com.aust.rakib.retrofitimageupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageShowActivity extends AppCompatActivity {

    private static String GET_URL="https://rakibjoarder.000webhostapp.com/GetImageResources.php";
    private static String BASE_URL="https://rakibjoarder.000webhostapp.com/";

    ImageView imageView;
    RecyclerView recyclerView;
    RecyclerviewAdpater recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        GridLayoutManager glm = new GridLayoutManager(this,2);
      //  LinearLayoutManager linearLayoutManager =new LinearLayoutManager(ImageShowActivity.this);
      //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(glm);


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<ArrayList<ImageGetModelClass>> arrayListCall=apiInterface.arrayListCall();
        arrayListCall.enqueue(new Callback<ArrayList<ImageGetModelClass>>() {
            @Override
            public void onResponse(Call<ArrayList<ImageGetModelClass>> call, Response<ArrayList<ImageGetModelClass>> response) {
                if(response.code()==200)
                {
                    recyclerViewAdapter =new RecyclerviewAdpater(response.body(),ImageShowActivity.this);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ImageGetModelClass>> call, Throwable t) {

            }
        });


    }
}
