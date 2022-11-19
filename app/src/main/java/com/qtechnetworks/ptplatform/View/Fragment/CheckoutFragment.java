package com.qtechnetworks.ptplatform.View.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.Subscription.Subscription;
import com.qtechnetworks.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class CheckoutFragment extends Fragment implements CallBack {

    Button confirmPurchase;
    RadioButton inAppPurchase, creditCard, payPal;
    RadioGroup inAppLayout;
    SubscriptionPackage subscriptionPackage;
    TextView total,subtotal;

    public CheckoutFragment(SubscriptionPackage subscriptionPackage){
        this.subscriptionPackage=subscriptionPackage;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        initials(view);

        subtotal.setText(subscriptionPackage.getPrice());
        total.setText(subscriptionPackage.getPrice());

        getPaymentUrl(subscriptionPackage.getId());

        confirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setFragment( new CheckoutWebviewFragment(subscriptionPackage));
            }
        });

//        inAppPurchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inAppLayout.setVisibility(View.VISIBLE);
//            }
//        });
//
//        creditCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inAppLayout.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        payPal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inAppLayout.setVisibility(View.INVISIBLE);
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }
    private void getPaymentUrl(int packageId){
        HashMap<String ,Object> params=new HashMap<>();

//        params.put("code", SinglePackageFragment.promoCode.getText().toString());
        params.put("package_id", packageId);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, General.class, params);
    }

    private void setFragment( Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    private void initials(View view) {
        confirmPurchase = view.findViewById(R.id.confirm_purchase_btn);
        total=view.findViewById(R.id.cart_total_value);
        subtotal=view.findViewById(R.id.subtotal_value);
//        inAppPurchase = view.findViewById(R.id.in_app_radiobutton);
//        creditCard = view.findViewById(R.id.credit_card_radiobutton);
//        payPal = view.findViewById(R.id.paypal_radiobutton);
//        inAppLayout = view.findViewById(R.id.in_app_layout);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        /*
        try {
            if (isSuccess) {

                General paymentResult = (General) result;

                paymentWebview.getSettings().setLoadsImagesAutomatically(true);
                paymentWebview.getSettings().setJavaScriptEnabled(true);
                paymentWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                paymentWebview.loadUrl(paymentResult.getData());
                paymentWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);

                        Log.d("WebView", "your current url when webpage loading.." + url);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        Log.d("WebView", "your current url when webpage loading.. finish" + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        // TODO Auto-generated method stub
                        String h = url;
                        Log.i("urlwebloadreco", url);

                        if (url.contains("payment/success")) {

//                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();

                            Fragment fragment = new SuccessFragment("Checkout");
                            setFragment(fragment);

                        }
                        if (url.contains("payment/error")) {

//                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();

                            Toast.makeText(getContext(), "Payment Failed!", Toast.LENGTH_SHORT).show();

                        }
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {


                        if (url.contains("payment/success")) {

//                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();

                            Fragment fragment = new SuccessFragment("Checkout");
                            setFragment(fragment);
                        }
                        if (url.contains("payment/error")) {

//                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();

                            Toast.makeText(getContext(), "Payment Failed!", Toast.LENGTH_SHORT).show();

                        }
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
            }else{
                Toast.makeText(getContext(), "Already subscribed", Toast.LENGTH_SHORT).show();
            }
            } catch(Exception e){
                Toast.makeText(getContext(), "Your request has timed out due to security reasons, please try again at a later time", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
//            Intent i = new Intent(getContext(), MainActivity.class);
//            startActivity(i);
                //setFragment(new HomeFragment());
            }


         */

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), R.string.already_subscribed, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStackImmediate();

    }

    @Override
    public void onComplete() {

    }
}