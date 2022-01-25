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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ContactFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NewsSingleFragment;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

    private Context context;
    private List<String> list;
    private List<Integer> listpic;

    public NewsAdapter(Context context) {

        this.list = list;
        this.context=context;
        this.listpic=listpic;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame ,new NewsSingleFragment(), (AppCompatActivity) v.getContext());
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
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView newsTitle, newsSubtitle, newsTime;
        public ImageView newsImg;
        private ConstraintLayout newsLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle=itemView.findViewById(R.id.news_title);
            newsSubtitle=itemView.findViewById(R.id.news_sub_title);
            newsTime=itemView.findViewById(R.id.news_time);
            newsImg=itemView.findViewById(R.id.news_img);
            newsLayout=itemView.findViewById(R.id.news_layout);

        }
    }

}
