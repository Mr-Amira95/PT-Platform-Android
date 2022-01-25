package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.NutritionFragment;

public class SetTargetCaloriesFragment extends Fragment {

    Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_target_calories, container, false);

        initials(view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new NutritionFragment());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        save = view.findViewById(R.id.save_btn);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}