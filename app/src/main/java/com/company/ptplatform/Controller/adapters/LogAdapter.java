package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.ptplatform.Model.Beans.FavoriteandWorkout.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.ExercisesSingleFragment;

import java.util.List;


public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder>  {

    private Context context;
    List<Datum> data;

    public LogAdapter(Context context, List<Datum> data) {
        this.context=context;
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_log_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= data.get(position);

        if (current.getTitle() != null)
            holder.logTitle.setText(current.getTitle());

        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.logImg);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.logLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (current.getTitle() != null || current.getDescription() != null)
                    setFragment(new ExercisesSingleFragment(),current.getId().toString(),current.getVideo(),current.getTitle(),current.getDescription(), current.getIsFavourite(), current.getIsTodayLog());
            }
        });

    }

    private void setFragment(Fragment fragment, String videoid, String video, String title, String description, Boolean isFavourite, Boolean isTodayLog) {

        Bundle args = new Bundle();

        args.putString("flag","log");

        args.putString("VideoID",videoid);

        args.putString("video",video);
        args.putString("title",title);
        args.putString("description",description);
        args.putString("is_fav",isFavourite.toString());
        args.putString("is_today",isTodayLog.toString());

        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView logTitle;
        public ImageView logImg;
        private ConstraintLayout logLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            logTitle=itemView.findViewById(R.id.log_title);
            logImg=itemView.findViewById(R.id.log_img);
            logLayout=itemView.findViewById(R.id.log_layout);
        }
    }

}
