package com.example.news.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.example.news.ModelClasses.Article;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * From ArticleDB")
    LiveData<List<Article>> getAll();

    @Query("SELECT COUNT() FROM articledb WHERE title = :title")
    int search(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Delete
    void delete(Article article);


}
