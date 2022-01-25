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

public class ChooseCoachFragment extends Fragment {

    RecyclerView coachRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_coach, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        coachRecyclerview = view.findViewById(R.id.coach_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachRecyclerview.setLayoutManager(gridLayoutManager);


        CoachAdapter coachAdapter = new CoachAdapter(getContext());
        coachRecyclerview.setAdapter(coachAdapter);
    }
}