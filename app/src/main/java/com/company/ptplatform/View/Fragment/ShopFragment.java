package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.ptplatform.Controller.adapters.PackageAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.Subscription.Subscription;
import com.company.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ShopFragment extends Fragment implements CallBack {

    RecyclerView subscriptionsPackageRecyclerview, ptPackagesRecyclerview;
    TextView subscriptionsTitle, ptPackagesTitle;
    PackageAdapter packageAdapter;
    PackageAdapter subscriptionAdapter;

    ArrayList <Boolean> inShopSubscription = new ArrayList<>();
    ArrayList <Boolean> inShopPersonal = new ArrayList<>();
    public static boolean haveSubscription = false;
    public static boolean havePersonalTrainer = false;

    String id = "x";

    public ShopFragment() {

    }

    public ShopFragment(String id) {
        this.id= id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        initials(view);
        getSubscriptions();


        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        subscriptionsPackageRecyclerview = view.findViewById(R.id.subscription_packages_recyclerview);
        ptPackagesRecyclerview = view.findViewById(R.id.pt_packages_recyclerview);

        subscriptionsTitle = view.findViewById(R.id.subscription_title);
        ptPackagesTitle = view.findViewById(R.id.pt_title);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.HORIZONTAL);
        subscriptionsPackageRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ptPackagesRecyclerview.setLayoutManager(linearLayoutManager);
    }

    private void getSubscriptions(){

        HashMap<String ,Object> params=new HashMap<>();

//        params.put("skip",skip);
        if (id.equalsIgnoreCase("x"))
            params.put("coach_id",PreferencesUtils.getCoach(getContext()).getId());
        else
            params.put("coach_id", id);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, Subscription.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        haveSubscription = false;
        havePersonalTrainer = false;

        if (isSuccess) {
            Subscription packages = (Subscription) result;

            for (SubscriptionPackage subscriptionPackage : packages.getData().getSubscription()) {
                if (subscriptionPackage.getUserPackage() != null) {
                    inShopSubscription.add(true);
                    haveSubscription = true;
                } else {
                    inShopSubscription.add(false);
                }
            }

            for (SubscriptionPackage subscriptionPackage : packages.getData().getPersonalTraining()) {
                if (subscriptionPackage.getUserPackage() != null) {
                    inShopPersonal.add(true);
                    havePersonalTrainer = true;
                } else {
                    inShopPersonal.add(false);
                }
            }

            if (packages.getData().getSubscription().size() == 0)
                subscriptionsTitle.setVisibility(View.GONE);

            if (packages.getData().getPersonalTraining().size() == 0)
                ptPackagesTitle.setVisibility(View.GONE);


            subscriptionAdapter = new PackageAdapter(getContext(), packages.getData().getSubscription(), inShopSubscription, "subscription", id);
            packageAdapter = new PackageAdapter(getContext(), packages.getData().getPersonalTraining(), inShopPersonal, "personal", id);

            ptPackagesRecyclerview.setAdapter(packageAdapter);
            subscriptionsPackageRecyclerview.setAdapter(subscriptionAdapter);
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}