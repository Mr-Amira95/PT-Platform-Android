package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.ptplatform.Model.Beans.Challenge.VideoChallengeData;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.ExercisesSingleFragment;

import java.util.ArrayList;
import java.util.List;

public class ChalengesVideosAdapter extends RecyclerView.Adapter<ChalengesVideosAdapter.ViewHolder>  {

    // Done

    private Context context;
    List<VideoChallengeData> data;

    public static ArrayList<Integer> completedVideosIds = new ArrayList<>();

    public ChalengesVideosAdapter(Context context, List<VideoChallengeData> data) {
        this.context=context;
        this.data=data;
    }


    @NonNull
    @Override
    public ChalengesVideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_challenge_video,parent,false);

        return new ChalengesVideosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChalengesVideosAdapter.ViewHolder holder, int position) {

        VideoChallengeData current= data.get(position);

        holder.challengeVideoName.setText(current.getVideo().getTitle());

        try{
            Glide.with(context).load(current.getVideo().getImage()).placeholder(R.drawable.logo).into(holder.challengeVideoImg);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach"))
            holder.completeCheckbox.setVisibility(View.GONE);

        holder.challengeVideoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ExercisesSingleFragment(),current.getVideo().getId().toString(),current.getVideo().getVideo(),current.getVideo().getTitle(),current.getVideo().getDescription());
            }
        });

        if(current.getIsComplete()){
            holder.completeCheckbox.setClickable(false);
            holder.completeCheckbox.setChecked(true);
        }

        holder.completeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")) {
                    holder.completeCheckbox.setChecked(holder.completeCheckbox.isChecked());
                }
            }
        });

        holder.completeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")) {
                    if(b) {
                        if (!completedVideosIds.contains(current.getId())) {
                            completedVideosIds.add(current.getId());
                        }
                    } else {
                        if (completedVideosIds.contains(current.getId())) {
                            completedVideosIds.remove(current.getId());
                        }
                    }
                }
            }
        });

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            holder.completeCheckbox.setClickable(false);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            holder.completeCheckbox.setVisibility(View.VISIBLE);
        }

    }


    private void setFragment(Fragment fragment,String videoid,String video,String title,String description) {

        Bundle args = new Bundle();

        args.putString("flag","log");

        args.putString("VideoID",videoid);

        args.putString("video",video);
        args.putString("title",title);
        args.putString("description",description);

        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView challengeVideoName;
        ImageView challengeVideoImg;
        ConstraintLayout challengeVideoLinearLayout;
        CheckBox completeCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            challengeVideoName=itemView.findViewById(R.id.video_name);
            challengeVideoImg=itemView.findViewById(R.id.video_image);
            challengeVideoLinearLayout=itemView.findViewById(R.id.challenge_video_layout);
            completeCheckbox=itemView.findViewById(R.id.complete_checkbox);
        }
    }

}
