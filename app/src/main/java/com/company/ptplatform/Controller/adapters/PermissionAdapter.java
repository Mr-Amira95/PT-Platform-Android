package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder>  {

    private Context context;
    private List<String> datum;
    private List<TextView> textViewList = new ArrayList<>();
    private String flag;

    public PermissionAdapter(Context context, List<String> datum) {
        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public PermissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_title,parent,false);

        return new PermissionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionAdapter.ViewHolder holder, int position) {

        String current= datum.get(position);

        holder.title.setText(current);

        //textViewList.add(holder.title);

    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);

        }
    }

}
