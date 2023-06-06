package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Coach;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Datum;
import com.qtechnetworks.ptplatform.Model.utilits.PrefKeys;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;


public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.ViewHolder>  {

    private Context context;
    private Coach coach;

    public CoachAdapter(Context context, Coach coach) {
        this.context=context;
        this.coach=coach;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current = coach.getData().get(position);

        holder.trainerName.setText(current.getLastName());
        Glide.with(context).load(current.getAvatar()).placeholder(R.drawable.user_default).into(holder.trainerImage);

        holder.coachLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesUtils.putString(PrefKeys.coachid,current.getId().toString());
                PreferencesUtils.setCoach(current, context);

                setFragment(R.id.home_frame ,new MainFragment(), (AppCompatActivity) v.getContext());
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

        return coach.getData().size();

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
