package com.example.news.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.news.API.NewsAPI;
import com.example.news.ModelClasses.Article;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {
    private static final String TAG = "NewsRepository";
    private final MutableLiveData<List<Article>> apiResponseMutableLiveData = new MutableLiveData<>();


    Calendar calendar = Calendar.getInstance();


    private static final NewsRepository repository = new NewsRepository();

    public static NewsRepository getInstance() {
        return repository;
    }

    public static Retrofit getNewsApiClient() {
        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LiveData<List<Article>> getArticles(String keyword) {
        NewsAPI newsAPICall = getNewsApiClient().create(NewsAPI.class);
        ApiResponse apiResponse = new ApiResponse();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        String API_KEY = "e866de2876a34769996a3b6f775bceaf";
        Call<ApiResponse> call = newsAPICall.getResponse(keyword, date, "publishedAt", "en", API_KEY);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(TAG, "onResponse:  Get Profile Data Failed ");
                } else {
                    Log.d(TAG, "Api call is successful");
                    apiResponse.setStatus(response.body().getStatus());
                    apiResponse.setTotalResults(response.body().getTotalResults());
                    apiResponse.setArticles(response.body().getArticles());
                    Log.d(TAG, "Response size is " + apiResponse.getTotalResults());
                    apiResponseMutableLiveData.setValue(apiResponse.getArticles());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure : News API Call failed" + t);
            }
        });

        return apiResponseMutableLiveData;
    }

}
