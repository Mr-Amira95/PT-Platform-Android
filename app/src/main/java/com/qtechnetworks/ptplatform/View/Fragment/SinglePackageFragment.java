package com.qtechnetworks.ptplatform.View.Fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.qtechnetworks.ptplatform.Controller.adapters.PermissionAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.FreePackage.FreePackageResults;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.PromoCode.PromoCodeResults;
import com.qtechnetworks.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SinglePackageFragment extends Fragment implements CallBack, PurchasesUpdatedListener {

    RecyclerView featuresRecyclerview;
    PermissionAdapter permissionsAdapter;
    SubscriptionPackage packages;
    TextView packageType,packageDesc,packageDate, packagePrice, packagePriceNew, packageData, discountValue, discountTitle;
    Button checkoutBtn, checkPromoCode;
    EditText promoCode;
    CheckBox agreement;

    RadioButton inAppPurchase, creditCard, payPal;

    String promo, paymentMethod, id;

    private BillingClient billingClient;

    public SinglePackageFragment(SubscriptionPackage packages, String id){
        this.packages=packages;
        this.id=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_package, container, false);

        initials(view);
        fillData();
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void connectBilling(String sku){

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                connectBilling(sku);
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                query(sku);
            }
        });
    }

    private void query (String sku){
        List<String> skuList = new ArrayList<>();
        skuList.add(sku);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                        purchaseFlow(list.get(0));
                    }
                });

    }

    private void purchaseFlow(SkuDetails itemInfo) {

        billingClient.launchBillingFlow(getActivity(), BillingFlowParams.newBuilder()
                        .setSkuDetails(itemInfo)
                        .build());
    }

    private void clicks() {

        checkPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!promoCode.getText().toString().isEmpty())
                    checkPromoCode();
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((agreement.getVisibility() == View.VISIBLE && agreement.isChecked()) || agreement.getVisibility() == View.GONE){

                    if (promo != null) {
                        if (creditCard.isChecked()){
                            paymentMethod = "stripe";
                            getPaymentUrl("stripe");
                        } else if (payPal.isChecked()){
                            paymentMethod = "paypal";
                            getPaymentUrl("paypal");
                        }
                    } else {
                        if (creditCard.isChecked()){
                            getPaymentUrlWithoutPromo("stripe");
                        } else if (payPal.isChecked()){
                            getPaymentUrlWithoutPromo("paypal");
                        } else if (inAppPurchase.isChecked()) {
                            connectBilling(packages.getPurchaseAndroidId());
//                            callProduct(packages.getPurchaseAndroidId());
                        }
                    }

                } else if (agreement.getVisibility() == View.VISIBLE){
                    Toast.makeText(getContext(), "please confirm that you take the responsibility", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getPaymentUrl(String paymentMethod){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("code", promoCode.getText().toString());
        params.put("payment_method", paymentMethod);
        params.put("package_id", packages.getId());
        if (id.equalsIgnoreCase("x"))
            params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId().toString());
        else
            params.put("coach_id", id);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, FreePackageResults.class, params);
    }

    private void getPaymentUrlWithoutPromo(String paymentMethod){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("payment_method", paymentMethod);
        params.put("package_id", packages.getId());

        if (id.equalsIgnoreCase("x"))
            params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId().toString());
        else
            params.put("coach_id", id);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, FreePackageResults.class, params);
    }

    private void fillData() {

        packageType.setText(packages.getName());
        packageDesc.setText(packages.getDescription());
        packageData.setText(packages.getDate() +" "+ getString(R.string.months));
        packagePrice.setText(packages.getPrice());
        discountTitle.setVisibility(View.GONE);
        discountValue.setVisibility(View.GONE);

        List<String> permissions=new ArrayList<>();

        if (packages.getPermissions()!=null) {
            permissions.add(getString(R.string.video_calls) + packages.getPermissions().getCallVideo().toString());
            permissions.add(getString(R.string.workout_schedule) + packages.getPermissions().getWorkoutSchedule().toString());
            permissions.add(getString(R.string.food_plan) + packages.getPermissions().getFoodPlan().toString());

            permissionsAdapter = new PermissionAdapter(getContext(),  permissions);
            featuresRecyclerview.setAdapter(permissionsAdapter);
        }

        if (ShopFragment.haveSubscription)
            agreement.setVisibility(View.VISIBLE);
        else
            agreement.setVisibility(View.GONE);

        if (packages.getPurchaseAndroidId() != null) {
            inAppPurchase.setVisibility(View.VISIBLE);
            promoCode.setVisibility(View.GONE);
            checkPromoCode.setVisibility(View.GONE);
        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setFragmentWithoutBack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.commit();
    }

    private void initials(View view) {
        agreement = view.findViewById(R.id.agreement);

        packagePriceNew = view.findViewById(R.id.package_price_new);
        discountValue = view.findViewById(R.id.discount_value);
        discountTitle = view.findViewById(R.id.discount_title);
        packageData = view.findViewById(R.id.package_data);

        packageType=view.findViewById(R.id.package_type);
        packageDesc=view.findViewById(R.id.package_desc);
        packageDate=view.findViewById(R.id.package_date);
        packagePrice=view.findViewById(R.id.package_price);

        inAppPurchase = view.findViewById(R.id.in_app_radiobutton);
        creditCard = view.findViewById(R.id.credit_card_radiobutton);
        payPal = view.findViewById(R.id.paypal_radiobutton);

        checkPromoCode = view.findViewById(R.id.promo_code_button);
        promoCode = view.findViewById(R.id.promo_code);

        featuresRecyclerview = view.findViewById(R.id.package_features_recyclerview);
        checkoutBtn = view.findViewById(R.id.checkout_btn);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        featuresRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        billingClient = BillingClient
                .newBuilder(getActivity())
                .setListener(this)
                .enablePendingPurchases()
                .build();

    }

    private void checkPromoCode(){

        HashMap<String ,Object> params = new HashMap<>();

        params.put("code", promoCode.getText().toString());
        params.put("package_id", String.valueOf(packages.getId()));

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Promo_Code_URL, AppConstants.Promo_Code_TAG, PromoCodeResults.class, params);
    }

    /*
    private void callProduct(String sku) {

        Call<General> call = RetrofitClient.getInstance().getMyApi().getProduct(sku);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, retrofit2.Response<General> response) {

                if (response.isSuccessful()){
                    General general = response.body();

                    if (general.getSuccess()){
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
                        setFragmentWithoutBack(new SuccessFragment("Checkout"));
                    } else {
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {
//                Toast.makeText(getContext(), R.string.an_error_has_occurred, Toast.LENGTH_LONG).show();
            }

        });
    }
    */
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

         if (isSuccess){

             switch (tag) {
                 case AppConstants.Promo_Code_TAG:

                     PromoCodeResults promoCodeResults = (PromoCodeResults) result;

                     packagePriceNew.setVisibility(View.VISIBLE);
                     packagePriceNew.setText(String.valueOf(promoCodeResults.getData().getPrice()));
                     packagePrice.setPaintFlags(packagePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                     discountTitle.setVisibility(View.VISIBLE);
                     discountValue.setVisibility(View.VISIBLE);
                     promoCode.setFocusable(false);
                     promoCode.setEnabled(false);
                     checkPromoCode.setClickable(false);
                     discountValue.setText(promoCodeResults.getData().getDiscount());

                     promo = promoCode.getText().toString();

                     break;

                 case AppConstants.PACKAGES_TAG:
                     FreePackageResults paymentResult = (FreePackageResults) result;

                     if (creditCard.isChecked()){
                         setFragment(new CheckoutWebviewFragment(paymentResult, "stripe"));
                     } else if (payPal.isChecked()){
                         setFragment(new CheckoutWebviewFragment(paymentResult, "paypal"));
                     }

                     break;

                 case AppConstants.PACKAGES_IN_APP_TAG:
                     setFragmentWithoutBack(new SuccessFragment("Checkout"));
                     break;
             }


         }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void buyPackage(String invoiceID) {

        HashMap<String ,Object> params=new HashMap<>();
        params.put("package_id", packages.getId());
        params.put("payment_method", "free");

        if (id.equalsIgnoreCase("x"))
            params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId().toString());
        else
            params.put("coach_id", id);

        params.put("txn_id", invoiceID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.PACKAGES_URL, AppConstants.PACKAGES_IN_APP_TAG, General.class, params);
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {

        if (billingResult.getResponseCode() == BillingClient.ConnectionState.CONNECTED && purchases != null) {
            Toast.makeText(getContext(), R.string.purchase_successfull, Toast.LENGTH_LONG).show();
            buyPackage(purchases.get(0).getPurchaseToken());
        } else if (billingResult.getResponseCode() == BillingClient.ConnectionState.CLOSED) {
            Toast.makeText(getContext(), billingResult.getDebugMessage(), Toast.LENGTH_LONG).show();

            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            Toast.makeText(getContext(), R.string.cancel, Toast.LENGTH_LONG).show();
        }

    }
}