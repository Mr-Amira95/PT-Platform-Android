package com.qtechnetworks.ptplatform.Controller.adapters.Unused;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ChooseCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ContactFragment;
import com.qtechnetworks.ptplatform.View.Fragment.FollowUsFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NewsFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    private List<Integer> listPic;

    public CategoryAdapter(List<String> list, List<Integer> listPic, Activity context) {

        this.list = list;
        this.context=context;
        this.listPic=listPic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(listPic.get(holder.getAdapterPosition())).into(holder.categoryImg);
        holder.categoryTitle.setText(list.get(holder.getAdapterPosition()));

        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()){
                    case 0:
                        setFragment(R.id.home_frame ,new FollowUsFragment(), (AppCompatActivity) v.getContext());
                        break;
                    case 1:
                        setFragment(R.id.home_frame ,new ChooseCoachFragment(), (AppCompatActivity) v.getContext());
                        break;
                    case 2:
                        setFragment(R.id.home_frame ,new ContactFragment(), (AppCompatActivity) v.getContext());
                        break;
                    case 3:
                        setFragment(R.id.home_frame ,new NewsFragment(), (AppCompatActivity) v.getContext());
                        break;
                }
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
        return listPic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTitle;
        public ImageView categoryImg;
        public LinearLayout categoryLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImg = itemView.findViewById(R.id.cate1_image);
            categoryTitle = itemView.findViewById(R.id.categ1_name);
            categoryLayout = itemView.findViewById(R.id.category_layout);
        }
    }

}
