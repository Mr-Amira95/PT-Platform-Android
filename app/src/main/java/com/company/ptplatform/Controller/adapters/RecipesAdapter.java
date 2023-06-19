package com.company.ptplatform.Controller.adapters;

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
import com.company.ptplatform.Model.Beans.Recipes.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.ExercisesSingleFragment;
import com.company.ptplatform.View.Fragment.PlansSingleFragment;
import com.company.ptplatform.View.Fragment.SupplementSingleFragment;
import com.company.ptplatform.View.Fragment.WorkoutSingleFragment;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>  {

    private Context context;
    private String flag;

    List<Datum> data;

    public RecipesAdapter(Context context, String flag, List<Datum> data) {

        this.context = context;
        this.flag = flag;
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_supplement_diet_plan,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= data.get(position);

        if (current.getTitle() != null)
            holder.title.setText(current.getTitle().toString());


        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.img);

        }catch (Exception e){
            e.printStackTrace();
        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("Exercises"))
                    setFragment(R.id.home_frame, new ExercisesSingleFragment(), (AppCompatActivity) v.getContext());
                else if (flag.equals("Workout"))
                    setFragment(R.id.home_frame, new WorkoutSingleFragment(), (AppCompatActivity) v.getContext());
                else if (flag.equals("Supplements"))
                    setFragment(new SupplementSingleFragment(), current.getTitle(), current.getImage(), current.getDescription());
                else if (flag.equals("Recipes and Diet Plans"))
                    setFragment(new PlansSingleFragment(current));
            }
        });
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment ) {

        Bundle args = new Bundle();

        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }


    private void setFragment(Fragment fragment,String title,String image,String desc) {

        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("image",image);
        args.putString("descrip",desc);

        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
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
