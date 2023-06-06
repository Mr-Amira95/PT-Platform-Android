package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
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
import com.qtechnetworks.ptplatform.Model.Beans.PersonalCoach.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.AddTraineeDetailsFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChallengesFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChatFirebaseFragment;
import com.qtechnetworks.ptplatform.View.Fragment.HistoryFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ProgressFragment;

import java.util.ArrayList;


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
            Glide.with(mContext).load(current.getAvatar()).placeholder(R.drawable.user_default).into(holder.trainerImage);
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
                        setFragment(new HistoryFragment(String.valueOf(current.getId())));
                        break;
                    case "kyc":
                        setFragment(new AddTraineeDetailsFragment(current.getId()));
                        break;
                    case "progress":
                        setFragment(new ProgressFragment(current.getId()));
                        break;
                    case "chat":
                        setFragment(new ChatFirebaseFragment(String.valueOf(current.getId())));
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
