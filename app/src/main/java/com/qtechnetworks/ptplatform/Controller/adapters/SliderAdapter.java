package com.qtechnetworks.ptplatform.Controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.R;

import java.util.List;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Integer> listPic;

    public SliderAdapter(List<Integer> listPic, Activity context) {
        this.context=context;
        this.listPic=listPic;
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return listPic.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View SliderLayout = inflater.inflate(R.layout.layout_slider, container, false);
        assert SliderLayout != null;

        ImageView img = SliderLayout.findViewById(R.id.slider_img);
        Glide.with(context).load(listPic.get(position)).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        //context.startActivity(new Intent(context.getApplicationContext(), FollowusActivity.class));
                        break;
                    case 1:
                        //context.startActivity(new Intent(context.getApplicationContext(), ChoosingCoachActivity.class));
                        break;
                    case 2:
                        //context.startActivity(new Intent(context.getApplicationContext(), ContactUsActivity.class));
                        break;
                }

            }
        });

        container.addView(SliderLayout, 0);
        return SliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }

}
