package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;

public class WorkoutSingleFragment extends Fragment {

    Button explore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_single, container, false);

        initials(view);

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new ExercisesSingleFragment());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        explore = view.findViewById(R.id.explore_btn);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}