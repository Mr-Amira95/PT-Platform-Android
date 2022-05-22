package com.qtechnetworks.ptplatform.Controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Datum;
import com.qtechnetworks.ptplatform.R;

import java.util.List;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Datum> listPic;

    public SliderAdapter(List<Datum> listPic, Activity context) {
        this.context=context;
        this.listPic=listPic;
        this.inflater = context.getLayoutInflater();
    }

    public SliderAdapter(List<Integer> listPic, FragmentActivity activity) {

    }

    @Override
    public int getCount() {
        try{
            return listPic.size();
        }catch (Exception e){
            return 0;
        }

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
        Glide.with(context).load(listPic.get(position).getImage().toString()).into(img);

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
