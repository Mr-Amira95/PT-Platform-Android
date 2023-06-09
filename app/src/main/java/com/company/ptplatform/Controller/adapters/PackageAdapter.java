package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.FreePackage.FreePackageResults;
import com.company.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.ShopFragment;
import com.company.ptplatform.View.Fragment.SinglePackageFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> implements CallBack {

    private Context context;
    private List<SubscriptionPackage> packages;

    ArrayList <Boolean> inShop;
    String flag, id;

    public PackageAdapter(Context context, List<SubscriptionPackage> packages, ArrayList<Boolean> inShopPersonal, String flag, String id) {
        this.packages=packages;
        this.context=context;
        inShop = inShopPersonal;
        this.flag = flag;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_package,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {

        if (inShop.get(position)){
            holder.buyNowBtn.setText(R.string.bought);
        } else if ((flag.equalsIgnoreCase("subscription") && ShopFragment.haveSubscription) || (flag.equalsIgnoreCase("personal") && ShopFragment.havePersonalTrainer)){
            holder.buyNowBtn.setText(R.string.cant_bought);
        }

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(context);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        holder.featureRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        List<String> permissions=new ArrayList<>();

        if(packages.get(position).getPermissions()!=null) {
            permissions.add("Video calls: "+packages.get(position).getPermissions().getCallVideo().toString());
            permissions.add("Workout Schedule: "+packages.get(position).getPermissions().getWorkoutSchedule().toString());
            permissions.add("Food Plan: "+packages.get(position).getPermissions().getFoodPlan().toString());

            PermissionAdapter permissionsAdapter = new PermissionAdapter(this.context,  permissions);
            holder.featureRecyclerview.setAdapter(permissionsAdapter);
        }

        holder.packageType.setText(packages.get(position).getName());
        holder.packageDesc.setText(packages.get(position).getDescription());
        holder.packageDate.setText(packages.get(position).getDate()+" months");
        holder.packagePrice.setText((int)packages.get(position).getPriceObject().getAmount() + " " + packages.get(position).getPriceObject().getCurrency());

        if(packages.get(position).getStyle().equalsIgnoreCase("style_bronze")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_bronze);
        } else if(packages.get(position).getStyle().equalsIgnoreCase("style_gold")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_gold);
        } else if(packages.get(position).getStyle().equalsIgnoreCase("style_silver")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_silver);
        } else {
            holder.backgroundLayout.setBackgroundResource(R.drawable.free_package_three);
        }

        holder.buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inShop.get(position)) {
                    if ((flag.equalsIgnoreCase("subscription") && ShopFragment.haveSubscription) || (flag.equalsIgnoreCase("personal") && ShopFragment.havePersonalTrainer)) {
                        Toast.makeText(context, "you can't buy another package before your current package ending", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!packages.get(position).getIsFree())
                            setFragment(R.id.home_frame ,new SinglePackageFragment(packages.get(holder.getAbsoluteAdapterPosition()), id), (AppCompatActivity) v.getContext());
                        else
                            buyPackage(packages.get(position).getId());
                    }
                } else {
                    Toast.makeText(context, "you already have this package", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buyPackage(int packageId) {

        HashMap<String ,Object> params=new HashMap<>();
        params.put("package_id", packageId);
        params.put("payment_method", "free");
        params.put("coach_id", PreferencesUtils.getCoach(context).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(context, AppConstants.PACKAGES_URL, AppConstants.PACKAGES_TAG, FreePackageResults.class, params);
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            Toast.makeText(context, "Your Free Trial Has Started, Enjoy most of our life-changing features for free and start paving your way to a healthier life! ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((MainActivity)context).finish();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView featureRecyclerview;
        Button buyNowBtn;
        TextView packageType,packageDesc,packageDate,packagePrice;
        ImageView backgroundLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             backgroundLayout=itemView.findViewById(R.id.background_layout);
            featureRecyclerview = itemView.findViewById(R.id.features_recuclerview);
            buyNowBtn = itemView.findViewById(R.id.buy_now);
            packageType=itemView.findViewById(R.id.package_type);
            packageDesc=itemView.findViewById(R.id.package_desc);
            packageDate=itemView.findViewById(R.id.package_date);
            packagePrice=itemView.findViewById(R.id.package_price);

        }
    }

}
