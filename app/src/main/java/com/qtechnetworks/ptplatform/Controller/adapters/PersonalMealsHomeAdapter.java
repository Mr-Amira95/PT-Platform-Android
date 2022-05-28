package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Model.Beans.MealsPersonal.Datum;
import com.qtechnetworks.ptplatform.R;

import java.util.List;


public class PersonalMealsHomeAdapter extends RecyclerView.Adapter<PersonalMealsHomeAdapter.ViewHolder>  {

    private Context context;
    List<Datum> data;

    public PersonalMealsHomeAdapter(Context context, List<Datum> data) {
        this.context=context;
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum current= data.get(position);


        holder.trainerName.setText(current.getTitle().toString());


        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.trainerImage);

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {

        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView trainerName;
        public RoundedImageView trainerImage;
        public LinearLayout coachLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainerImage=itemView.findViewById(R.id.trainer_image);
            trainerName=itemView.findViewById(R.id.trainer_name);
            coachLayout=itemView.findViewById(R.id.coach_layout);

        }
    }

}
