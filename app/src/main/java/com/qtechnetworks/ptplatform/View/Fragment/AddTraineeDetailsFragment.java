package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;

public class AddTraineeDetailsFragment extends Fragment {

    TextView doneBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trainee_details, container, false);

        initials(view);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new SuccessFragment("Checkout"));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initials(View view) {
        doneBtn = view.findViewById(R.id.done_txt);
    }
}