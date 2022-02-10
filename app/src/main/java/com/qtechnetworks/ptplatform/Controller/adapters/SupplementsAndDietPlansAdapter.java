package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.PlansSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.SupplementSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.WorkoutSingleFragment;

import java.util.List;

public class SupplementsAndDietPlansAdapter extends RecyclerView.Adapter<SupplementsAndDietPlansAdapter.ViewHolder>  {

    private Context context;
    private List<String> list;
    private List<Integer> listpic;
    private String flag;

    public SupplementsAndDietPlansAdapter(Context context, String flag) {
        this.list = list;
        this.context = context;
        this.listpic = listpic;
        this.flag = flag;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_supplement_diet_plan,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("Exercises"))
                    setFragment(R.id.home_frame, new ExercisesSingleFragment(), (AppCompatActivity) v.getContext());
                else if (flag.equals("Workout"))
                    setFragment(R.id.home_frame, new WorkoutSingleFragment(), (AppCompatActivity) v.getContext());
                else if (flag.equals("Supplements"))
                    setFragment(R.id.home_frame, new SupplementSingleFragment(), (AppCompatActivity) v.getContext());
                else if (flag.equals("Recipes and Diet Plans"))
                    setFragment(R.id.home_frame, new PlansSingleFragment(), (AppCompatActivity) v.getContext());
            }
        });
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView img;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            img=itemView.findViewById(R.id.img);
            layout=itemView.findViewById(R.id.layout);
        }
    }

}
