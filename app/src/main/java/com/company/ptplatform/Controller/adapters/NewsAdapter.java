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
import com.company.ptplatform.Model.Beans.News.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.NewsSingleFragment;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> data;

    public NewsAdapter(Context context, List<Datum> data) {

        this.data = data;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= data.get(position);


        try{

            Glide.with(context).load(current.getImage()).placeholder(R.drawable.logo).into(holder.newsImg);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.newsTitle.setText(current.getTitle());


        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new NewsSingleFragment(String.valueOf(current.getId())));
            }
        });

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();

//        args.putString("image",image);
//        args.putString("title",title);
//        args.putString("description",description);
//
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView newsTitle, newsSubtitle, newsTime;
        public ImageView newsImg;
        private ConstraintLayout newsLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle=itemView.findViewById(R.id.news_title);
            newsTime=itemView.findViewById(R.id.news_time);
            newsImg=itemView.findViewById(R.id.news_img);
            newsLayout=itemView.findViewById(R.id.news_layout);

        }
    }

}
