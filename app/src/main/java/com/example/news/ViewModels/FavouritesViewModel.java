package com.example.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.ModelClasses.Article;
import com.example.news.Repository.DBRepository;

import java.util.List;
import java.util.Objects;

public class FavouritesViewModel extends ViewModel {

    private static final String TAG = "FavouritesViewModel";
    private final LiveData<List<Article>> favouritesLiveData;
    private final DBRepository dbRepository = new DBRepository();

    public FavouritesViewModel() {
        super();
//        favourites = favouritesDB.userDao().getAll();
        favouritesLiveData = dbRepository.getFavourites();

    }


    public LiveData<List<Article>> getFavouritesLiveData() {
        return favouritesLiveData;
    }

    public void insertFavourite(Article article) {
            dbRepository.insertFavourite(article);
//        }
    }

}
