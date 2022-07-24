package com.example.news;

import android.content.Context;

import com.example.news.DataBase.DataBase;

public class Aplication {
    private static DataBase db;

    public Aplication(Context context) {
        db = DataBase.getINSTANCE(context);
    }

    public static DataBase getInstance(){
        return db;
    }
}
