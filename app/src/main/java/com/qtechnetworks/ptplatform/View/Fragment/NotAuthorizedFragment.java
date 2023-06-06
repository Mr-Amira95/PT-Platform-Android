package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qtechnetworks.ptplatform.R;

public class NotAuthorizedFragment extends Fragment {

    Button subscribeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_authorized, container, false);

        initial(view);
        clicks();

        return view;
    }

    private void clicks() {

        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(R.id.home_frame, new ShopFragment());
            }
        });
    }

    private void initial(View v) {

        subscribeBtn = v.findViewById(R.id.subscribe_btn);

    }


    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}


