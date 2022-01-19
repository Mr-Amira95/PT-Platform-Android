package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.R;

public class NewsActivity extends AppCompatActivity {

    RecyclerView news_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initial();

    }

    private void initial(){

        news_recyclerview=findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(NewsActivity.this);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);

        news_recyclerview.setLayoutManager(layoutManagerhorizantalleader);


        NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext());
        news_recyclerview.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();


    }

}