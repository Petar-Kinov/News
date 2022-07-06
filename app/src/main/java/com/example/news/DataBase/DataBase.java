package com.example.news.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.news.ModelClasses.Article;

@Database(entities = {Article.class},version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static DataBase INSTANCE;

    public static DataBase getINSTANCE(Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBase.class,"Database")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}
