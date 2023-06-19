package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.FoodHome.Snack;
import com.company.ptplatform.R;

import java.util.List;


public class NutritionHistoryAdapter extends RecyclerView.Adapter<NutritionHistoryAdapter.ViewHolder>  {

    private Context context;
    private List<Snack> data;
    private String flag;

    public NutritionHistoryAdapter(Context context, List<Snack> data, String flag) {
        this.context=context;
        this.data=data;
        this.flag=flag;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nutrition_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Snack current = data.get(position);
        holder.title.setText(current.getName());
        holder.desc.setText("Carbs: " + current.getCarb() + ", Fat: " + current.getFat() + ", Protein: " + current.getProtein() );
        holder.calories.setText("Calories: " + current.getCalorie());
    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.nutrition_title);
            desc = itemView.findViewById(R.id.nutrition_desc);
            calories = itemView.findViewById(R.id.nutrition_calories);
        }
    }

}
