package com.example.news.DataBase;

import androidx.core.view.WindowInsetsCompat;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.news.ModelClasses.Article;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * From ArticleDB")
    List<Article> getAll();


    @Insert
    void insert(Article article);

    @Delete
    void delete(Article article);

}
