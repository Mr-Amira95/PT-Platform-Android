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

import com.company.ptplatform.Model.Beans.FoodHome.Dinner;
import com.company.ptplatform.R;

import java.util.List;


public class FoodDinnerAdapter extends RecyclerView.Adapter<FoodDinnerAdapter.ViewHolder>  {

    private Context context;
    List<Dinner> data;


    public FoodDinnerAdapter(Context context, List<Dinner> data) {
        this.context=context;
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dinner current= data.get(position);

        holder.food_title.setText(current.getTitle().toString());
        holder.food_details.setText(current.getName().toString());
        holder.food_value.setText(current.getCalorie().toString());

    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView food_title,food_details,food_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            food_title=itemView.findViewById(R.id.food_title);
            food_details=itemView.findViewById(R.id.food_details);
            food_value=itemView.findViewById(R.id.food_value);

        }
    }

}
