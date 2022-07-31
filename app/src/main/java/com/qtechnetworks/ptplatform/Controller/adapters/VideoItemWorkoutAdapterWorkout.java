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

public class VideoItemWorkoutAdapterWorkout extends RecyclerView.Adapter<VideoItemWorkoutAdapterWorkout.ViewHolder>  {

    private Context context;
    private List<Datum> data;
    private ExercisesSingleFragment exercisesSingleFragment;
    TextView add_to_favourite; TextView add_to_workout; TextView add_to_log;


    public VideoItemWorkoutAdapterWorkout(Context context, List<Datum> data, ExercisesSingleFragment exercisesSingleFragment, TextView add_to_favourite, TextView add_to_workout, TextView add_to_log) {

        this.context = context;
        this.data=data;
        this.exercisesSingleFragment=exercisesSingleFragment;
        this.add_to_favourite=add_to_favourite;
        this.add_to_log=add_to_log;
        this.add_to_workout=add_to_workout;

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

        if (current.getIsFavourite()){
            add_to_favourite.setText("Remove from favourite");
        }
        if (current.getIsTodayLog()){
            add_to_log.setText("Remove from log");
        }
        if (current.getIsWorkout()){
            add_to_workout.setText("Remove from Workout");
        }

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

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    exercisesSingleFragment.desc.setText(Html.fromHtml(current.getDescription().toString(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    exercisesSingleFragment.desc.setText(Html.fromHtml(current.getDescription().toString()));
                }

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
