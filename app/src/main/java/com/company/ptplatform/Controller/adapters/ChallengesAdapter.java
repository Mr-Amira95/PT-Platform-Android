package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.ptplatform.Model.Beans.Challenge.ChallengeData;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Dialogs.NoteDialog;
import com.company.ptplatform.View.Fragment.ChallengesFragment;
import com.company.ptplatform.View.Fragment.ChallengesSignleFragment;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.ViewHolder>  {

    // Done

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

        holder.descriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDialog noteDialog = new NoteDialog(context, current.getDescription());
                noteDialog.show();
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
        ConstraintLayout challengeLinearLayout;
        Button descriptionBtn;

        CheckBox completeCheckbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            challengeName=itemView.findViewById(R.id.challenge_name);
            ChallengeImg=itemView.findViewById(R.id.challenge_img);
            challengeLinearLayout=itemView.findViewById(R.id.challenge_layout_item);
            descriptionBtn=itemView.findViewById(R.id.description_btn);

        }
    }

}
