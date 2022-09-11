package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.Subscription.SubscriptionPackage;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.SinglePackageFragment;

import java.util.ArrayList;
import java.util.List;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder>  {

    private Context context;
    private PermissionAdapter permissionsAdapter;
    private List<SubscriptionPackage> packages;
    public PackageAdapter(Context context,List<SubscriptionPackage> packages) {
        this.packages=packages;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_package,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(context);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        holder.featureRecyclerview.setLayoutManager(layoutManagerhorizantalleader);
        List<String> permissions=new ArrayList<>();
        if(packages.get(position).getPermissions()!=null){
        permissions.add("Video calls: "+packages.get(position).getPermissions().getCallVideo().toString());
        permissions.add("Workout Schedule: "+packages.get(position).getPermissions().getWorkoutSchedule().toString());
        permissions.add("Food Plan: "+packages.get(position).getPermissions().getFoodPlan().toString());

        permissionsAdapter = new PermissionAdapter(this.context,  permissions);
            holder.featureRecyclerview.setAdapter(permissionsAdapter);
        }

        holder.packageType.setText(packages.get(position).getName());
        holder.packageDesc.setText(packages.get(position).getDescription());
        holder.packageDate.setText(packages.get(position).getDate()+" months");
        holder.packagePrice.setText(packages.get(position).getPrice());
        if(packages.get(position).getName().equals("BRONZE")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_bronze);
        }
        if(packages.get(position).getName().equals("GOLD")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_gold);
        }
        if(packages.get(position).getName().equals("SILVER")){
            holder.backgroundLayout.setBackgroundResource(R.drawable.background_package_silver);
        }
        holder.buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame ,new SinglePackageFragment(packages.get(holder.getAbsoluteAdapterPosition())), (AppCompatActivity) v.getContext());
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView featureRecyclerview;
        Button buyNowBtn;
        TextView packageType,packageDesc,packageDate,packagePrice;
        ConstraintLayout backgroundLayout;
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
