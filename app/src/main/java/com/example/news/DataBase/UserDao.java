package com.example.news.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.news.ModelClasses.Article;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * From ArticleDB")
    LiveData<List<Article>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Delete
    void delete(Article article);

}
