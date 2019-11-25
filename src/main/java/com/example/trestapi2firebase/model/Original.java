package com.example.trestapi2firebase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Original {
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("width")
    @Expose
    private Integer width;


    @SerializedName("height")
    @Expose
    private Integer height;

    public Original() {

    }

    public Original(Integer height, String url, Integer width ) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
