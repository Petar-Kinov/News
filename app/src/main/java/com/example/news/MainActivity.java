package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.news.Adapters.ViewPagerAdapter;
import com.example.news.Fragments.FavouritesFragment;
import com.example.news.Fragments.NewsFragment;
import com.example.news.Fragments.SpareFragment;
import com.example.news.Repository.NewsRepository;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private final String TAG = "Debug";

    private final NewsRepository newsRepository = NewsRepository.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(MainActivity.this);
        viewPagerAdapter.addFragment(new NewsFragment());
        viewPagerAdapter.addFragment(new FavouritesFragment());
        viewPagerAdapter.addFragment(new SpareFragment());

        viewPager2.setAdapter(viewPagerAdapter);




        //News Observer
//        mNewsViewModel.getNews().observe(this, new Observer<List<NewsStory>>() {
//            @Override
//            public void onChanged(List<NewsStory> newsStories) {
//
//            }
//        });

        //Tab bar on tab selected functionality
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}