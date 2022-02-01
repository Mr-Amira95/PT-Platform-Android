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

public class ShopFragment extends Fragment {

    RecyclerView subscriptionsPackageRecyclerview, ptPackagesRecyclerview;
    PackageAdapter packageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        subscriptionsPackageRecyclerview = view.findViewById(R.id.subscription_packages_recyclerview);
        ptPackagesRecyclerview = view.findViewById(R.id.pt_packages_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.HORIZONTAL);
        subscriptionsPackageRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ptPackagesRecyclerview.setLayoutManager(linearLayoutManager);

        packageAdapter = new PackageAdapter(getContext());
        ptPackagesRecyclerview.setAdapter(packageAdapter);
        subscriptionsPackageRecyclerview.setAdapter(packageAdapter);
    }
}