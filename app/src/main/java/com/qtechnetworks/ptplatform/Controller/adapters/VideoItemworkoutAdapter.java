package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutVideo.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;

import java.io.IOException;
import java.util.List;

public class VideoItemworkoutAdapter extends RecyclerView.Adapter<VideoItemworkoutAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> data;
    private ExercisesSingleFragment exercisesSingleFragment;


    public VideoItemworkoutAdapter(Context context, List<Datum> data, ExercisesSingleFragment exercisesSingleFragment) {

        this.context = context;
        this.data=data;
        this.exercisesSingleFragment=exercisesSingleFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum current= data.get(position);

        holder.title.setText(current.getTitle().toString());
       // holder.time.setText();

//        if (current.getIsFavourite()){
//            ExercisesSingleFragment.add_to_favourite.setText("Remove from favourite");
//        }
//        if (current.getIsTodayLog()){
//            ExercisesSingleFragment.add_to_log.setText("Remove from log");
//        }
//        if (current.getIsWorkout()){
//            ExercisesSingleFragment.add_to_workout.setText("Remove from Workout");
//        }

        exercisesSingleFragment.VideoID=current.getId().toString();

        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.video_view);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.video_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exercisesSingleFragment.VideoID=current.getId().toString();

                try {
                    exercisesSingleFragment.video_view.setDefaultArtwork(exercisesSingleFragment.drawableFromUrl(current.getImage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                exercisesSingleFragment.desc.setText(Html.fromHtml(current.getDescription().toString()));
                exercisesSingleFragment.playinitial(current.getVideo());
            }
        });


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, time;
        public ImageView video_view;
        private ConstraintLayout video_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
            video_view=itemView.findViewById(R.id.video_view);
            video_layout=itemView.findViewById(R.id.video_layout);
        }
    }

}
