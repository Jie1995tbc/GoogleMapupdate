package com.example.trestapi2firebase.model;

public class Venue {
    private Address address;

    private String ageRestriction;

    private String capacity;

    private String id;

    private String latitude;

    private String longitude;

    private String name;

    private String resourceUri;

    public Venue() {

    }

    public Venue(Address address, String ageRestriction, String capacity, String id, String latitude, String longitude, String name, String resourceUri) {
        this.address = address;
        this.ageRestriction = ageRestriction;
        this.capacity = capacity;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.resourceUri = resourceUri;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public Address getAddress() {
        return address;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getResourceUri() {
        return resourceUri;
    }
}
