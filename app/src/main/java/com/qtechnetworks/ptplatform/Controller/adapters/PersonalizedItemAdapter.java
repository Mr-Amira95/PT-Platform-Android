package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Model.Beans.Personalized.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Activity.MediaViewActivity;
import com.qtechnetworks.ptplatform.View.Fragment.PersonalTrainingInnerFragment;

public class PersonalizedItemAdapter extends RecyclerView.Adapter<PersonalizedItemAdapter.ViewHolder>  {

    public static final  int VIDEO_PERSONAL_TAG=1;
    public static final int IMAGE_PERSONAL_TAG=2;
    public static final int NOTE_PERSONAL_TAG=3;
    private Context context;
    private Datum data;
    private int Rtype;
    private int size=0;
    Intent i;

    public PersonalizedItemAdapter(Context context,Datum data,int Rtype) {
        this.context=context;
        this.data=data;
        this.Rtype=Rtype;
        switch (Rtype){
            case PersonalizedItemAdapter.VIDEO_PERSONAL_TAG:
                size=data.getVideo().size();
                break;
            case PersonalizedItemAdapter.IMAGE_PERSONAL_TAG:
                size=data.getImage().size();
                break;
            case PersonalizedItemAdapter.NOTE_PERSONAL_TAG:
                size=data.getPdf().size();
                break;
        }
    }


    @NonNull
    @Override
    public PersonalizedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_personlise,parent,false);

        return new PersonalizedItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalizedItemAdapter.ViewHolder holder, int position) {

        switch (Rtype){
            case PersonalizedItemAdapter.VIDEO_PERSONAL_TAG:
                i=new Intent((MainActivity)context, MediaViewActivity.class).putExtra("url",data.getVideo().get(position).getValue());
                holder.dataName.setText("View video");
                break;
            case PersonalizedItemAdapter.IMAGE_PERSONAL_TAG:
                i=new Intent((MainActivity)context, MediaViewActivity.class).putExtra("url",data.getImage().get(position).getValue());

                holder.dataName.setText("View Image");
                break;
            case PersonalizedItemAdapter.NOTE_PERSONAL_TAG:
                i=new Intent((MainActivity)context, MediaViewActivity.class).putExtra("url","https://drive.google.com/viewerng/viewer?embedded=true&url=" +data.getPdf().get(position).getValue());

                holder.dataName.setText("View Note");
                break;
        }

        holder.coachLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Rtype){
                    case PersonalizedItemAdapter.VIDEO_PERSONAL_TAG:
                        setFragment(new PersonalTrainingInnerFragment(Rtype, data.getVideo().get(position).getValue()));
                        break;
                    case PersonalizedItemAdapter.IMAGE_PERSONAL_TAG:
                        setFragment(new PersonalTrainingInnerFragment(Rtype, data.getImage().get(position).getValue()));
                        break;
                    case PersonalizedItemAdapter.NOTE_PERSONAL_TAG:
                        setFragment(new PersonalTrainingInnerFragment(Rtype, data.getPdf().get(position).getValue()));
                        break;
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {

        return size;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dataName;
        public RoundedImageView trainerImage;
        public LinearLayout coachLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainerImage=itemView.findViewById(R.id.trainer_image);
            dataName=itemView.findViewById(R.id.trainer_name);
            coachLayout=itemView.findViewById(R.id.coach_layout);

        }
    }

}
