package com.example.news.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Adapters.RecyclerAdapter;
import com.example.news.Aplication;
import com.example.news.DataBase.DataBase;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;
import com.example.news.Repository.DBRepository;
import com.example.news.ViewModels.FavouritesViewmodel;
import com.example.news.WebViewActivity;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment implements RecyclerAdapter.OnArticleClickedListener {

    private final String TAG = "Debug";
    private RecyclerView favouritesRecyclerView;
    private ArrayList<Article> favourites;
    private FavouritesViewmodel favouritesViewModel;
//    private SearchView searchView;
    private DataBase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        favouritesRecyclerView = view.findViewById(R.id.favouritesView);
        db = Aplication.getInstance();

        favourites = new ArrayList<>();
        //Recycler Adapter
        setRecyclerAdapter();
        favouritesViewModel = new ViewModelProvider(requireActivity()).get(FavouritesViewmodel.class);
        favouritesViewModel.getFavouritesLiveData().observe(requireActivity(), dbResponse -> {
            favourites.clear();
            favourites.addAll(dbResponse.getFavourites());
            setRecyclerAdapter();
        });

        //TODO Search in Favourites

        // Search view functionality
//        searchView = view.findViewById(R.id.searchView);
//        searchView.setQueryHint("Search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                articlesViewModel.setKeyword(searchView.getQuery().toString());
//                searchView.clearFocus();
////                searchView.setIconified(true);
//                return false;
//            }

//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });


        return view;

    }
    private void setRecyclerAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), favourites, this, FragmentEnum.FAVOURITES.name());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        favouritesRecyclerView.setLayoutManager(layoutManager);
        favouritesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favouritesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(int position) {
        openWebViewActivity(favourites.get(position).getUrl());
    }

    @Override
    public void favouriteClickListener(int position) {
        saveFavourite(favourites.get(position));
        Log.d(TAG,String.valueOf(db.userDao().getAll().size()));
    }

    @Override
    public void deleteClickListener(int position) {
        deleteFavourite(favourites.get(position));
    }


    public void openWebViewActivity(String url){
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }

    private void saveFavourite(Article article){
        Log.d(TAG,"Saved to fabvourites");

        db.userDao().insert(article);
    }

    private void deleteFavourite(Article article){
        Log.d(TAG, String.valueOf(article.id));
        db.userDao().delete(article);
    }
}