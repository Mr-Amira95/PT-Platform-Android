package com.qtechnetworks.ptplatform.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.qtechnetworks.ptplatform.Controller.GoogleNetworking.RetrofitClient;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;

public class ShopTestActivity extends AppCompatActivity {

    TextView productName;
    Button buyItem;
    BillingClient billingClient;
    SkuDetails itemInfo;

    int qty = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_test);

        productName = findViewById(R.id.product_name);
        buyItem = findViewById(R.id.buy_btn);

        String api = "https://androidpublisher.googleapis.com/androidpublisher/v3/applications/com.qtechnetworks.ptplatform/inappproducts/subcription_unit";

        callProduct("subcription_unit");

//        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
//            @Override
//            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
//                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
//                    for(Purchase purchase: list) {
//                        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
//                                !purchase.isAcknowledged()) {
////                                    verifyPurchase(purchase);
//                        }
//                    }
//                }
//            }
//        }).build();
//        connectToGooglePlayBilling();

        buyItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        billingClient.launchBillingFlow(
                                ShopTestActivity.this,
                                BillingFlowParams.newBuilder().setSkuDetails(itemInfo).build());
                    }
                }
        );

    }

        private void callProduct(String sku) {

        Call<General> call = RetrofitClient.getInstance().getMyApi().getProduct(sku);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, retrofit2.Response<General> response) {

                if (response.isSuccessful()){
                    General general = response.body();

                    if (general.getSuccess()){
//                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
//
//                        Intent i = new Intent(getContext(), MainActivity.class);
//                        startActivity(i);
//                        getActivity().finish();
                    } else {
//                        Toast.makeText(this, String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    Toast.makeText(getContext(), R.string.the_message_must_be_at_least_20_characters, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {
//                Toast.makeText(getContext(), R.string.an_error_has_occurred, Toast.LENGTH_LONG).show();
            }

        });
    }


    private void connectToGooglePlayBilling(){
        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connectToGooglePlayBilling();
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            getProductDetails();
                        }
                    }
                }
        );
    }

    private void getProductDetails(){

        List<String> productIds = new ArrayList<>();
        productIds.add("subcription_unit");
        SkuDetailsParams getProductDetailsQuery = SkuDetailsParams
                .newBuilder()
                .setSkusList(productIds)
                .setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.querySkuDetailsAsync(
                getProductDetailsQuery,
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                                list != null) {
                            itemInfo = list.get(0);
                            fillInfo();
                        }
                    }
                }
        );

    }

    private void fillInfo() {
        double price = ((double)itemInfo.getPriceAmountMicros())/1000000 * qty * 10;

        productName.setText(itemInfo.getDescription());
        buyItem.setText(String.valueOf(price));
    }


}