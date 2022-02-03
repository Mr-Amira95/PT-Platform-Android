package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.R;

public class ChallengesFragment extends Fragment {

    private RecyclerView otherRecyclerview, historyRecyclerview;
    private CoachAdapter adapter;
    private Button beginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        initials(view);

        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new ChallengesSignleFragment());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        beginBtn = view.findViewById(R.id.begin_btn);

        otherRecyclerview = view.findViewById(R.id.other_challenges_recyclerview);
        historyRecyclerview = view.findViewById(R.id.history_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        otherRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        historyRecyclerview.setLayoutManager(linearLayoutManager1);

        adapter = new CoachAdapter(getContext());
        otherRecyclerview.setAdapter(adapter);
        historyRecyclerview.setAdapter(adapter);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}