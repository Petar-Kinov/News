package com.example.news.API;

import com.example.news.Repository.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsAPI {


    @GET("v2/everything")
    Call<ApiResponse> getResponse(@Query("q") String keyword,
                                    @Query("from") String form,
                                    @Query("sortBy") String publishedAt,
                                    @Query("language") String language,
                                    @Query("apiKey") String apiKey);

}
