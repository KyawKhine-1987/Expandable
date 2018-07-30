package com.freelance.android.expandablerecyclerview.model;

import com.google.gson.annotations.SerializedName;

public class Hero {

    @SerializedName("name")
    private String name;

    @SerializedName("realname")
    private String realName;

    @SerializedName("team")
    private String team;

    @SerializedName("firstappearance")
    private String firstAppearance;

    @SerializedName("createdby")
    private String createdBy;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("imageurl")
    private String imageUrl;

    @SerializedName("bio")
    private String bio;

    public Hero(String name, String realName, String team, String firstAppearance, String createdBy, String publisher, String imageUrl, String bio) {
        this.name = name;
        this.realName = realName;
        this.team = team;
        this.firstAppearance = firstAppearance;
        this.createdBy = createdBy;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getRealName() {
        return realName;
    }

    public String getTeam() {
        return team;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBio() {
        return bio;
    }
}
