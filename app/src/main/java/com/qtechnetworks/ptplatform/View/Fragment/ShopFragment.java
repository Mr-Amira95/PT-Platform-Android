package com.qtechnetworks.ptplatform.View.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.qtechnetworks.ptplatform.Controller.adapters.PackageAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Recipes.Recipes;
import com.qtechnetworks.ptplatform.Model.Beans.Subscription.Subscription;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ShopFragment extends Fragment implements CallBack {

    RecyclerView subscriptionsPackageRecyclerview, ptPackagesRecyclerview;
    PackageAdapter packageAdapter;
    PackageAdapter subscriptionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        initials(view);
        getSubscriptions(PreferencesUtils.getCoach(getContext()).getId().toString());


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
    }

    private void getSubscriptions(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

      //  params.put("skip",skip);
          params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, Subscription.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){
            case AppConstants.PACKAGES_TAG:
                if(isSuccess) {
                    Subscription packages = (Subscription) result;
                    packageAdapter = new PackageAdapter(getContext(),packages.getData().getPersonalTraining());
                    subscriptionAdapter = new PackageAdapter(getContext(),packages.getData().getSubscription());
                    ptPackagesRecyclerview.setAdapter(packageAdapter);
                    subscriptionsPackageRecyclerview.setAdapter(subscriptionAdapter);
                }
                break;

        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}