package com.example.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.news.Repository.ApiResponce;
import com.example.news.Repository.NewsRepository;

public class ArticlesViewModel extends ViewModel {

    private final String TAG = "Debug";
    private final NewsRepository newsRepository = NewsRepository.getInstance();
    private final LiveData<ApiResponce> apiResponseLiveData;
    private final SavedStateHandle savedStateHandle;

    public ArticlesViewModel(SavedStateHandle savedStateHandle){
        super();
        this.savedStateHandle = savedStateHandle;
        LiveData<String> keywordLiveData = savedStateHandle.getLiveData("keyword");

        //TODO add language to the savedStateHandle
        apiResponseLiveData = Transformations.switchMap(keywordLiveData, keyword -> newsRepository.getArticles(keyword));

    }

    public LiveData<ApiResponce> getApiResponseLiveData(){
        Log.d(TAG,"getApiResponseLive data called");
        return apiResponseLiveData;

    }

    // the word that is being searched
    public void setKeyword(String keyword){
        savedStateHandle.set("keyword",keyword);
        Log.d(TAG,"Saved state handle set to : " + keyword);
    }

}
