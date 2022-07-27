package com.example.news.Fragments;

import android.app.Application;
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
import com.example.news.Aplication;
import com.example.news.DataBase.DataBase;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;
import com.example.news.Repository.ApiResponce;
import com.example.news.ViewModels.ArticlesViewModel;
import com.example.news.ViewModels.FavouritesViewmodel;
import com.example.news.WebViewActivity;

import java.util.ArrayList;


public class NewsFragment extends Fragment implements RecyclerAdapter.OnArticleClickedListener {

    private final String TAG = "Debug";
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<Article> articles;
    private ArticlesViewModel articlesViewModel;
    private FavouritesViewmodel favouritesViewmodel;
    private SearchView searchView;
    private DataBase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        db = Aplication.getInstance();

        articles = new ArrayList<>();
        //Recycler Adapter
        setRecyclerAdapter();

        favouritesViewmodel =new ViewModelProvider(this).get(FavouritesViewmodel.class);

        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);
        articlesViewModel.getApiResponseLiveData().observe(requireActivity(), apiResponce -> {
            Log.d(TAG, "Live data changed. Size is " + apiResponce.getTotalResults());
            articles.clear();
            articles.addAll(apiResponce.getArticles());

            setRecyclerAdapter();
        });

        // Search view functionality
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                articlesViewModel.setKeyword(searchView.getQuery().toString());
                searchView.clearFocus();
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
        adapter = new RecyclerAdapter(getContext(),articles, this, FragmentEnum.NEWS.name());
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

        addFavouritesRunnable addFavouritesRunnable = new addFavouritesRunnable(articles.get(position), favouritesViewmodel);
        new Thread(addFavouritesRunnable).start();
    }

    @Override
    public void deleteClickListener(int position) {
        // This button is hidden in the news fragment so this can remain empty
    }


    public void openWebViewActivity(String url){
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }

//    private void saveFavourite(Article article){
//        Log.d(TAG,"Saved to fabvourites");
//
//        db.userDao().insert(article);
//        adapter.notifyItemInserted(adapter.getItemCount());
//    }
}

// Background thread for adding favourites
class addFavouritesRunnable implements Runnable{
    Article article;
    FavouritesViewmodel favouritesViewmodel;

    // TODO figure out how to access FavouritesViewModel witchout passing it through the favouritesClickListener
    addFavouritesRunnable(Article article, FavouritesViewmodel favouritesViewmodel){
        this.article = article;
        this.favouritesViewmodel = favouritesViewmodel;
    }

    @Override
    public void run() {
            favouritesViewmodel.insertFavourite(article);
    }
}