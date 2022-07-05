package com.example.news.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.news.API.NewsAPI;
import com.example.news.ModelClasses.Article;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {
    //    private static NewsRepository instance;
    private MutableLiveData<ArrayList<Article>> articlesMutableLiveData = new MutableLiveData<>();
    private static Retrofit retrofit;
    private NewsAPI newsAPICall;
    private MutableLiveData<ApiResponce> apiResponceMutableLiveData = new MutableLiveData<>();

    private final String TAG = "Debug";
    Calendar calendar = Calendar.getInstance();
    private final String API_KEY = "e866de2876a34769996a3b6f775bceaf";

//    //Singleton
//    public static NewsRepository getInstance(){
//        if (instance == null){
//            return new NewsRepository();
//        }
//        return instance;
//    }

    private static final NewsRepository repository = new NewsRepository();
    public static NewsRepository getInstance(){
        return repository;
    }

    public static Retrofit getNewsApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public LiveData<ApiResponce> getArticles(String keyword){
        newsAPICall = getNewsApiClient().create(NewsAPI.class);
//        ArrayList<Article> articlesArrayList = new ArrayList<>();
        ApiResponce apiResponce = new ApiResponce();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        Call<ApiResponce> call = newsAPICall.getResponce(keyword, date ,"publishedAt" ,"en", API_KEY);

//        call.enqueue(new Callback<List<Article>>() {
//            @Override
//            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
////                for (int i = 0; i < response.body().size(); i++){
////                    Article article = new Article();
////                    article.setTitle(response.body().get(i).getTitle());
////                    articlesArrayList.add(article);
////                    Log.d(TAG,"Responce body is " + response.body().toString());
////                }
////                articlesMutableLiveData.setValue(articlesArrayList);
////            }
//
//            @Override
//            public void onFailure(Call<List<Article>> call, Throwable t) {
//                Log.d(TAG,"onFailure : News API Call failed" + t.toString());
//            }
//        });
        call.enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d(TAG, "onResponse:  Get Profile Data Failed ");
                } else {
                    Log.d(TAG,"Api call is successful");
                    ApiResponce apiResponce = new ApiResponce();
                    apiResponce.setStatus(response.body().getStatus());
                    apiResponce.setTotalResults(response.body().getTotalResults());
                    apiResponce.setArticles(response.body().getArticles());
                    Log.d(TAG,"Responce size is " + apiResponce.getTotalResults());
                    apiResponceMutableLiveData.setValue(apiResponce);
                }

            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d(TAG,"onFailure : News API Call failed" + t.toString());
            }
        });

        return apiResponceMutableLiveData;
    }

}
