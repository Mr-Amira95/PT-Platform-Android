package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.R;

import java.util.List;


public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.ViewHolder>  {

    private Context context;
    private View view;
    private List<String> list;
    private List<Integer> listpic;

    public CoachAdapter(Context context) {

        this.list = list;
        this.context=context;
        this.listpic=listpic;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {

        return 20;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categ_name;
        public RoundedImageView cate_image;
        private LinearLayout category_lienear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categ_name=itemView.findViewById(R.id.categ_name);
            cate_image=itemView.findViewById(R.id.cate_image);
            category_lienear=itemView.findViewById(R.id.category_lienear);

        }
    }

}
