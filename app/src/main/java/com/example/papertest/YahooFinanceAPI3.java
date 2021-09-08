package com.example.papertest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YahooFinanceAPI3 {
    //d0ffaaa9e2d8233074614579254213dc second api key
    //00b91ceac4f7f02ade54e246057fa0b5 first api key
    @GET("GOOGL?apikey=00b91ceac4f7f02ade54e246057fa0b5")
    Call<List<Post>> getPosts();
}