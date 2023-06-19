package com.company.ptplatform.Controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.company.ptplatform.Model.Beans.Banner.Datum;
import com.company.ptplatform.R;

import java.util.List;


public class SliderInnerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Datum> listPic;

    public SliderInnerAdapter(List<Datum> listPic, Activity context) {
        this.context=context;
        this.listPic=listPic;
        this.inflater = context.getLayoutInflater();
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
        View SliderLayout = inflater.inflate(R.layout.layout_slider_inner, container, false);
        assert SliderLayout != null;

        ImageView img = SliderLayout.findViewById(R.id.slider_img);
        Glide.with(context).load(listPic.get(position).getImage().toString()).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPic.get(position).getUrl() != null){
                    Uri uri = Uri.parse(listPic.get(position).getUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
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
