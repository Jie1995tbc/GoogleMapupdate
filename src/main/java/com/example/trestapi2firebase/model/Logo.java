package com.example.trestapi2firebase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo {

    @SerializedName("crop_mask")
    @Expose
    private CropMask cropMask;

    @SerializedName("original")
    @Expose
    private Original original;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("aspect_ratio")
    @Expose
    private String aspectRatio;

    @SerializedName("edge_color")
    @Expose
    private String edgeColor;

    @SerializedName("edge_color_set")
    @Expose
    private boolean edgeColorSet;

    public Logo() {

    }
    public Logo(String aspectRatio,CropMask cropMask, String edgeColor,boolean edgeColorSet,String id,Original original, String url) {
        this.cropMask = cropMask;
        this.original = original;
        this.id = id;
        this.url = url;
        this.aspectRatio = aspectRatio;
        this.edgeColor = edgeColor;
        this.edgeColorSet = edgeColorSet;
    }

    public CropMask getCropMask() {
        return cropMask;
    }

    public void setCropMask(CropMask cropMask) {
        this.cropMask = cropMask;
    }

    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getEdgeColor() {
        return edgeColor;
    }

    public void setEdgeColor(String edgeColor) {
        this.edgeColor = edgeColor;
    }

    public Boolean getEdgeColorSet() {
        return edgeColorSet;
    }

    public void setEdgeColorSet(Boolean edgeColorSet) {
        this.edgeColorSet = edgeColorSet;
    }
}
