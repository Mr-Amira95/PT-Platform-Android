package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.company.ptplatform.R;

public class ContactFragment extends Fragment {

    Button feedbackBtn, technicalSupportBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new FeedbackAndSupportFragment("Feedback"));
            }
        });


        technicalSupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new FeedbackAndSupportFragment("Technical Support"));
            }
        });
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initials(View view) {
        feedbackBtn = view.findViewById(R.id.feedback_button);
        technicalSupportBtn = view.findViewById(R.id.technical_support_button);
    }
}