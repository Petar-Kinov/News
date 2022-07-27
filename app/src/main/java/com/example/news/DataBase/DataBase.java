package com.example.news.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.news.Aplication;
import com.example.news.ModelClasses.Article;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Article.class},version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static DataBase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DataBase getINSTANCE(Context context) {
        if(INSTANCE == null){
            synchronized (DataBase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBase.class,"Database")
                        .build();
            }
        }
        return INSTANCE;
    }

}
