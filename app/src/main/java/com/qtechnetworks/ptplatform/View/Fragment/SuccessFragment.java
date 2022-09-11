package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;

public class SuccessFragment extends Fragment {

    TextView title, msg;
    String flag;

    public SuccessFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);

        initials(view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragment(R.id.home_frame, new MainFragment());
            }
        },3000);


        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        title = view.findViewById(R.id.title);
        msg = view.findViewById(R.id.message);

        if (flag.equals("Calendar")){
            title.setText("Request Sent!");
            msg.setText("Your request is awaiting your PT’s approval. We’ll notify you soon! ");
        } else if (flag.equals("Checkout")) {
            title.setText("Purchase Successful!");
            msg.setText("Enjoy the benefits of your subscription, and start the journey of change now");
        }

    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}