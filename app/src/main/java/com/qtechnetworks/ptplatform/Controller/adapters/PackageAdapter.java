package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.R;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder>  {

    private Context context;
    private TitleAdapter titleAdapter;

    public PackageAdapter(Context context) {
        this.context=context;
        titleAdapter = new TitleAdapter(this.context, "Features");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_package,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(context);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        holder.featureRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        holder.featureRecyclerview.setAdapter(titleAdapter);
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView featureRecyclerview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            featureRecyclerview = itemView.findViewById(R.id.features_recuclerview);

        }
    }

}