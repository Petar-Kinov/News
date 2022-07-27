package com.example.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.news.ModelClasses.Article;
import com.example.news.Repository.NewsRepository;

import java.util.List;

public class ArticlesViewModel extends ViewModel {

    private static final String TAG = "ArticlesViewModel";
    private final LiveData<List<Article>> apiResponseLiveData;
    private final SavedStateHandle savedStateHandle;

    public ArticlesViewModel(SavedStateHandle savedStateHandle){
        super();
        this.savedStateHandle = savedStateHandle;
        LiveData<String> keywordLiveData = savedStateHandle.getLiveData("keyword");

        //TODO add language to the savedStateHandle
        NewsRepository newsRepository = NewsRepository.getInstance();
        apiResponseLiveData = Transformations.switchMap(keywordLiveData, newsRepository::getArticles);

    }

    public LiveData<List<Article>> getApiResponseLiveData(){
        Log.d(TAG,"getApiResponseLive data called");
        return apiResponseLiveData;

    }

    // the word that is being searched
    public void setKeyword(String keyword){
        savedStateHandle.set("keyword",keyword);
        Log.d(TAG,"Saved state handle set to : " + keyword);
    }

}
