package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.FavoriteandWorkout.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NewsSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.WorkoutSingleFragment;

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

        holder.logTitle.setText(current.getTitle().toString());

        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.logImg);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.logLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ExercisesSingleFragment(),current.getId().toString(),current.getVideo(),current.getTitle().toString(),current.getDescription().toString());
            }
        });

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
