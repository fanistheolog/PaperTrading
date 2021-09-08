package com.example.papertest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YahooFinanceAPI {
    //d0ffaaa9e2d8233074614579254213dc second api key
    //00b91ceac4f7f02ade54e246057fa0b5 first api key
    @GET("TSLA?apikey=00b91ceac4f7f02ade54e246057fa0b5")
    Call<List<Post>> getPosts();
}