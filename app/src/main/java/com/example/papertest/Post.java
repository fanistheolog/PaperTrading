package com.example.papertest;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    List<post> prices;
    private double open;
    private double low;
    private double high;
    private double close;
    private String time;
    private int volume;
    private String title;
    @SerializedName("body")
    private String text;
    public double getUserId() {
        return open;
    }
    public double getId() {
        return low;
    }
    public double getTitle() {
        return high;
    }
    public double getText() {
        return close;
    }
    public String getTime() {
        return time;
    }

    class post {
        private int userId;
        private int id;
        private String title;

    }
}