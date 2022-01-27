package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.FoodAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.RecentItemAdapter;
import com.qtechnetworks.ptplatform.R;

public class FoodSingleFragment extends Fragment {

    TextView title;
    String flag;

    RecyclerView recentItemRecyclerview;
    RecentItemAdapter recentItemAdapter;

    public FoodSingleFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_single, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        title = view.findViewById(R.id.title);
        title.setText(flag);
        recentItemRecyclerview= view.findViewById(R.id.recent_item_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        recentItemRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        recentItemAdapter = new RecentItemAdapter(getContext());
        recentItemRecyclerview.setAdapter(recentItemAdapter);

    }

}