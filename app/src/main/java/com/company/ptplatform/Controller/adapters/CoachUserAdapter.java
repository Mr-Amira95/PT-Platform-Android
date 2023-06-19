package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.company.ptplatform.Model.Beans.Coach.Datum;
import com.company.ptplatform.Model.utilits.PrefKeys;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.MainFragment;

import java.util.List;


public class CoachUserAdapter extends RecyclerView.Adapter<CoachUserAdapter.ViewHolder>  {

    private Context context;
    List<Datum> data;

    public CoachUserAdapter(Context context, List<Datum> data) {
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

        holder.coachLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.getSubscription()) {
                    PreferencesUtils.putString(PrefKeys.coachid,current.getId().toString());
                    PreferencesUtils.setCoach(current, context);
                    setFragment(new MainFragment(),current.getId().toString(),current.getLastName(),String.valueOf(current.getAvatar()));
                } else {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("flag","shop");
                    String coachID = String.valueOf(current.getId());
                    i.putExtra("id", coachID);
                    PreferencesUtils.putString(PrefKeys.coachid, coachID);
                    ((MainActivity) context).startActivity(i);
                }
            }
        });


        try{
            Glide.with(context).load(current.getAvatar()).placeholder(R.drawable.user_default).into(holder.trainerImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.trainerName.setText(current.getLastName());
    }

    private void setFragment(Fragment fragment ,String coachid, String name, String image) {

        Bundle args = new Bundle();

        args.putString("coachid",coachid);
        args.putString("name",name);
        args.putString("image",image);

        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

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
