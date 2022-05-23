package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Category;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercise;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.PlansSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.SupplementSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.WorkoutSingleFragment;

import java.security.AccessControlContext;
import java.util.List;
import java.util.Locale;

public class ChestAndBicepsAdapter extends RecyclerView.Adapter<ChestAndBicepsAdapter.ViewHolder>  {

    private Context context;
    List<Exercise> category;
    private String flag;

    public ChestAndBicepsAdapter(Context context, String flag, List<Exercise> category) {

        this.context = context;
        this.flag = flag;
        this.category=category;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_img_txt,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Exercise current=category.get(position);

        try{

            Glide.with(context).load(current.getIcon()).placeholder(R.drawable.logo).into(holder.img);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.title.setText(current.getTitle().toString());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("Exercises"))
                    setFragment( new ExercisesSingleFragment(),"Exercises",current.getId().toString());
                else if (flag.equals("Workout"))
                    setFragment( new WorkoutSingleFragment(),current.getTitle().toString(),current.getDescription().toString(),current.getId().toString());
                else if (flag.equals("Supplements"))
                    setFragment( new SupplementSingleFragment());
                else if (flag.equals("Recipes and Diet Plans"))
                    setFragment( new PlansSingleFragment());
            }
        });
    }

    private void setFragment(Fragment fragment,String title,String description,String id) {

        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("description",description);
        args.putString("ID",id);
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragment(Fragment fragment,String flag,String id) {

        Bundle args = new Bundle();
        args.putString("flag",flag);
        args.putString("ID",id);
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return category.size();
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
