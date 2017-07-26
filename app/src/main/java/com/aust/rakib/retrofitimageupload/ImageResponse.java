package com.aust.rakib.retrofitimageupload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Personal on 7/26/2017.
 */

public class ImageResponse {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("response")
    @Expose
    private String response;


    public String getName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}

