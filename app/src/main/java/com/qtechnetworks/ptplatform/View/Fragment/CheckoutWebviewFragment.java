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
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Model.Beans.FreePackage.FreePackageResults;
import com.qtechnetworks.ptplatform.R;

public class CheckoutWebviewFragment extends Fragment {

    FreePackageResults paymentResult;
    WebView paymentWebview;

    int counter = 0;
    String flag;

    public CheckoutWebviewFragment(FreePackageResults paymentResult, String flag){
        this.paymentResult=paymentResult;
        this.flag=flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checkout_webview, container, false);

        paymentWebview=view.findViewById(R.id.payment_webview);

        paymentWebview.getSettings().setLoadsImagesAutomatically(true);
        paymentWebview.getSettings().setJavaScriptEnabled(true);
        paymentWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        paymentWebview.loadUrl(paymentResult.getData().getUrl());
        paymentWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebView", "your current url when webpage loading.." + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {

                if (url.contains("success")) {
                    counter++;
                    if (!flag.equalsIgnoreCase("paypal") || counter > 3) {
                        setFragment(new SuccessFragment("Checkout"));
                    }
                }

                if (url.contains("error")) {
                    Toast.makeText(getContext(), R.string.payment_failed, Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }

                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("success")) {
                    setFragment(new SuccessFragment("Checkout"));
                }

                if (url.contains("error")) {
                    Toast.makeText(getContext(), R.string.payment_failed, Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setFragment( Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}