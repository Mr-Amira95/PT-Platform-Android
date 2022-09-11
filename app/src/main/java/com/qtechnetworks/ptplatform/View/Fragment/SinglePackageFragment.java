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
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.PermissionAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

public class SinglePackageFragment extends Fragment {

    RecyclerView featuresRecyclerview;
    PermissionAdapter permissionsAdapter;
   SubscriptionPackage packages;
    TextView packageType,packageDesc,packageDate,packagePrice;
    Button checkoutBtn;
     public SinglePackageFragment(SubscriptionPackage packages){
        this.packages=packages;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_package, container, false);

        initials(view);


        packageType.setText(packages.getName());
       packageDesc.setText(packages.getDescription());

       packagePrice.setText(packages.getPrice()+" | "+packages.getDate()+" months");
        List<String> permissions=new ArrayList<>();
        if(packages.getPermissions()!=null){
            permissions.add("Video calls: "+packages.getPermissions().getCallVideo().toString());
            permissions.add("Workout Schedule: "+packages.getPermissions().getWorkoutSchedule().toString());
            permissions.add("Food Plan: "+packages.getPermissions().getFoodPlan().toString());

            permissionsAdapter = new PermissionAdapter(getContext(),  permissions);
            featuresRecyclerview.setAdapter(permissionsAdapter);
        }

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new CheckoutFragment(packages));
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
        packageType=view.findViewById(R.id.package_type);
        packageDesc=view.findViewById(R.id.package_desc);
        packageDate=view.findViewById(R.id.package_date);
        packagePrice=view.findViewById(R.id.package_price);
       /* featuresAdapter = new TitleAdapter(getContext(), "Features", exercise.getData().get(0));
        featuresRecyclerview.setAdapter(featuresAdapter);*/

    }
}