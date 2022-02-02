package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.PackageAdapter;
import com.qtechnetworks.ptplatform.R;

public class PersonalTrainingUnsubscribedFragment extends Fragment {

    RecyclerView packageRecyclerview;
    PackageAdapter packageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_training_unsubscribed, container, false);

        initials(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        packageRecyclerview = view.findViewById(R.id.packages_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.HORIZONTAL);
        packageRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        packageAdapter = new PackageAdapter(getContext());
        packageRecyclerview.setAdapter(packageAdapter);
    }
}