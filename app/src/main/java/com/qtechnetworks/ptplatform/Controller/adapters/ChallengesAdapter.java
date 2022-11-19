package com.qtechnetworks.ptplatform.Controller.adapters;

import static com.qtechnetworks.ptplatform.Model.utilits.PrefKeys.userID;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.ChallengeData;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Breakfast;
import com.qtechnetworks.ptplatform.Model.utilits.PrefKeys;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.CoachesDialog;
import com.qtechnetworks.ptplatform.View.Fragment.ChallengesFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ChallengesSignleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.ViewHolder>  {

    private Context context;
    List<ChallengeData> data;

    public ChallengesAdapter(Context context, List<ChallengeData> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ChallengesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_challenge_item,parent,false);

        return new ChallengesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesAdapter.ViewHolder holder, int position) {
        ChallengeData current= data.get(position);

        holder.challengeName.setText(current.getTitle());

        Glide.with(context).load(current.getIcon()).placeholder(R.drawable.logo).into(holder.ChallengeImg);

        holder.challengeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
                    setFragment(new ChallengesSignleFragment(current.getId(), String.valueOf(ChallengesFragment.userID)));
                else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
                    setFragment(new ChallengesSignleFragment(current.getId()));
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= ((MainActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView challengeName;
        ImageView ChallengeImg;
        LinearLayout challengeLinearLayout;

        CheckBox completeCheckbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            challengeName=itemView.findViewById(R.id.challenge_name);
            ChallengeImg=itemView.findViewById(R.id.challenge_img);
            challengeLinearLayout=itemView.findViewById(R.id.challenge_layout_item);

        }
    }

}
