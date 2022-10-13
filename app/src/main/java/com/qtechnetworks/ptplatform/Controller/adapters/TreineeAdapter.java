package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
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
import com.qtechnetworks.ptplatform.Model.Beans.PersonalCoach.Datum;
import com.qtechnetworks.ptplatform.Model.utilits.PrefKeys;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.CoachesDialog;
import com.qtechnetworks.ptplatform.View.Fragment.AddTraineeDetailsFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChallengesFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChatFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChatSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ProgressFragment;

import java.util.ArrayList;
import java.util.List;


public class TreineeAdapter extends RecyclerView.Adapter<TreineeAdapter.ViewHolder>  {

    private Context mContext;
    private String flag;
    private ArrayList<Datum> data;

    public TreineeAdapter(Context mContext, ArrayList<Datum> data, String flag) {
        this.flag = flag;
        this.mContext=mContext;
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

        Datum current = data.get(position);
        holder.trainerName.setText(current.getName());

        try {
            Glide.with(mContext).load(current.getAvatar()).placeholder(R.drawable.logo).into(holder.trainerImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.coachLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (flag){
                    case "challenges":
                        setFragment(new ChallengesFragment(current.getId()));
                        break;
                    case "history":
//                        setFragment(new ChallengesFragment());
                        break;
                    case "kyc":
                        setFragment(new AddTraineeDetailsFragment(current.getId()));
                        break;
                    case "progress":
                        setFragment(new ProgressFragment(current.getId()));
                        break;
                    case "chat":
                        setFragment(new ChatSingleFragment(current.getId()));
                        break;
                }

            }
        });

    }

    private void setFragment(Fragment fragment) {

        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();
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
