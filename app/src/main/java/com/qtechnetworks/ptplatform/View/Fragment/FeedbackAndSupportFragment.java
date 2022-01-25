package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;

public class FeedbackAndSupportFragment extends Fragment {

    TextView title;
    String flag="";

    public FeedbackAndSupportFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_and_support, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        title = view.findViewById(R.id.title_textview);
        title.setText(flag);
    }

}