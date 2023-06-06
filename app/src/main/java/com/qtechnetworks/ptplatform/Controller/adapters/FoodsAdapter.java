package com.qtechnetworks.ptplatform.Controller.adapters;

import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.Foodname_spinner;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.calories_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.carb_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.fat_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.protine_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.weightnumber_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.Food.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.FoodDialog;
import com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment;

import java.util.List;


public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder>  {

    private Context mContext;
    private List<Datum> data;
    FoodDialog foodDialog;

    public FoodsAdapter(Context mContext, List<Datum> data, FoodDialog foodDialog) {
        this.mContext=mContext;
        this.data=data;
        this.foodDialog=foodDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current = data.get(position);

        holder.coachName.setText(current.getName());

        holder.coachName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double carb=current.getCarb() * Integer.parseInt(weightnumber_edit.getText().toString());
                double fat=current.getFat() * Integer.parseInt(weightnumber_edit.getText().toString());
                double protein=current.getProtein() * Integer.parseInt(weightnumber_edit.getText().toString());
                double calories=current.getCalorie() * Integer.parseInt(weightnumber_edit.getText().toString());
                fat_text.setText(fat+"");
                carb_text.setText(carb+"");
                protine_text.setText(protein+"");
                calories_text.setText(calories+"");
                Foodname_spinner.setText(current.getName());
                FoodAddFragment.selectedFoodIndex = position;
                foodDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView coachName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coachName=itemView.findViewById(R.id.coach_name);
        }
    }

}
