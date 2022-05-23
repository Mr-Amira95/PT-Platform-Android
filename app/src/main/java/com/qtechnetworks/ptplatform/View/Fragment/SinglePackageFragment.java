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

import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.R;

public class SinglePackageFragment extends Fragment {

    RecyclerView featuresRecyclerview;
    TitleAdapter featuresAdapter;

    Button checkoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_package, container, false);

        initials(view);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new CheckoutFragment());
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
        featuresRecyclerview = view.findViewById(R.id.package_features_recyclerview);
        checkoutBtn = view.findViewById(R.id.checkout_btn);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        featuresRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

       /* featuresAdapter = new TitleAdapter(getContext(), "Features", exercise.getData().get(0));
        featuresRecyclerview.setAdapter(featuresAdapter);*/

    }
}