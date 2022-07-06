package com.example.news.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.news.Adapters.RecyclerAdapter;
import com.example.news.DataBase.DataBase;
import com.example.news.ModelClasses.Article;
import com.example.news.R;
import com.example.news.Repository.ApiResponce;
import com.example.news.ViewModels.ArticlesViewModel;
import com.example.news.WebViewActivity;

import java.util.ArrayList;


public class NewsFragment extends Fragment implements RecyclerAdapter.OnArticleClickedListener {

    private RecyclerView recyclerView;

    private ArrayList<Article> articles;
    private final String TAG = "Debug";
    private ArticlesViewModel articlesViewModel;
    private SearchView searchView;
    private DataBase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        articles = new ArrayList<>();
        //Recycler Adapter
        setRecyclerAdapter();
        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);
        articlesViewModel.getApiResponseLiveData().observe(requireActivity(), new Observer<ApiResponce>() {
            @Override
            public void onChanged(ApiResponce apiResponce) {
                Log.d(TAG,"Live data changed. Size is " + apiResponce.getTotalResults());
                articles.clear();
                for (int i = 0; i < apiResponce.getArticles().size() ; i++){
                    articles.add(apiResponce.getArticles().get(i));
                }

                setRecyclerAdapter();
            }
        });

        // Search view functionality
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                articlesViewModel.setKeyword(searchView.getQuery().toString());
                searchView.clearFocus();
//                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return view;

    }
    private void setRecyclerAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(),articles, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(int position) {
        openWebViewActivity(articles.get(position).getUrl());
    }

    @Override
    public void favouriteClickListener(int position) {
        saveFavourite(articles.get(position));
        Log.d(TAG,String.valueOf(db.userDao().getAll().size()));
    }


    public void openWebViewActivity(String url){
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }

    private void saveFavourite(Article article){
        db = DataBase.getINSTANCE(this.getContext());
        Log.d(TAG,"Saved to fabvourites");

        db.userDao().insert(article);
    }
}