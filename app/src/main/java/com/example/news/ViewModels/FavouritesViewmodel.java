package com.example.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.DataBase.DataBase;
import com.example.news.ModelClasses.Article;
import com.example.news.Repository.DBRepository;
import com.example.news.Aplication;
import com.example.news.Repository.DBResponce;

import java.util.ArrayList;
import java.util.List;

public class FavouritesViewmodel extends ViewModel {

    private final String TAG = "Debug";
    private final DataBase favouritesDB = Aplication.getInstance();
    private final LiveData<DBResponce> favouritesLiveData;
    private  List<Article> favourites = new ArrayList<>();
    private final DBRepository dbRepository = new DBRepository();

    public FavouritesViewmodel( ){
        super();
//        favourites = favouritesDB.userDao().getAll();
        favouritesLiveData = dbRepository.getFavourites();

    }


    public LiveData<DBResponce> getFavouritesLiveData(){
        Log.d(TAG,"getApiResponseLive data called");
        return favouritesLiveData;

    }


}
