package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qtechnetworks.ptplatform.R;

public class CheckoutFragment extends Fragment {

    Button confirmPurchase;
    RadioButton inAppPurchase, creditCard, payPal;
    RadioGroup inAppLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        initials(view);

        confirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new AddTraineeDetailsFragment());
            }
        });

        inAppPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inAppLayout.setVisibility(View.VISIBLE);
            }
        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inAppLayout.setVisibility(View.INVISIBLE);
            }
        });

        payPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inAppLayout.setVisibility(View.INVISIBLE);
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
        confirmPurchase = view.findViewById(R.id.confirm_purchase_btn);
        inAppPurchase = view.findViewById(R.id.in_app_radiobutton);
        creditCard = view.findViewById(R.id.credit_card_radiobutton);
        payPal = view.findViewById(R.id.paypal_radiobutton);
        inAppLayout = view.findViewById(R.id.in_app_layout);
    }
}