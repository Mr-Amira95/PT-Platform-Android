package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesWorkoutFragment;

import java.util.ArrayList;
import java.util.List;


public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> datum;
    private List<TextView> textViewList = new ArrayList<>();
    private String flag;

    public TitleAdapter(Context context, String flag, List<Datum> datum) {
        this.flag = flag;
        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_title,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= datum.get(position);

        holder.title.setText(current.getTitle().toString());

        textViewList.add(holder.title);

        textViewList.get(0).setBackgroundResource(R.drawable.background_radius_20_title);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("Workout") || flag.equals("Exercises")){
                    for (int i=0; i<textViewList.size(); i++){
                        if (i == holder.getAdapterPosition()){
                            textViewList.get(i).setBackgroundResource(R.drawable.background_radius_20_title);
                            ExercisesWorkoutFragment.setItems(current.getId().toString(), context, flag);
                            ExercisesWorkoutFragment.groupID = current.getId();
                        } else {
                            textViewList.get(i).setBackgroundResource(R.drawable.background_empty);
                        }
                    }
                }

            }
        });
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);

        }
    }

}
