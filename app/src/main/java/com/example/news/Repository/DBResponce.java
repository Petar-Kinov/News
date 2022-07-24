package com.example.news.Repository;

import com.example.news.ModelClasses.Article;

import java.util.ArrayList;

public class DBResponce {
    private ArrayList<Article> favourites;

    public ArrayList<Article> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Article> favourites) {
        this.favourites = favourites;
    }
}
