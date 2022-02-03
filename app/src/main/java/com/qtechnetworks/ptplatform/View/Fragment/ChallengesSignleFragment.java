package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.R;

public class ChallengesSignleFragment extends Fragment {

    private RecyclerView challengesRecyclerview;
    private CoachAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_signle, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        challengesRecyclerview = view.findViewById(R.id.challenges_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        challengesRecyclerview.setLayoutManager(gridLayoutManager);

        adapter = new CoachAdapter(getContext());
        challengesRecyclerview.setAdapter(adapter);

    }
}