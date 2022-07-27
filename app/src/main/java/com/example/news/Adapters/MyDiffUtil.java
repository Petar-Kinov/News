package com.example.news.Adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.example.news.ModelClasses.Article;

import java.util.List;

public class MyDiffUtil extends DiffUtil.Callback {
    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
