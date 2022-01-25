package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.R;

public class NewsFragment extends Fragment {

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        newsRecyclerview= view.findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerview.setLayoutManager(layoutManagerhorizantalleader);


        newsAdapter = new NewsAdapter(getContext());
        newsRecyclerview.setAdapter(newsAdapter);

    }
}