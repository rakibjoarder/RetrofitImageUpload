package com.aust.rakib.retrofitimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Personal on 7/26/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("UploadImage.php")
    Call<ImageResponse>uploadImage(@Field("title")String title,@Field("image")String image);
}
