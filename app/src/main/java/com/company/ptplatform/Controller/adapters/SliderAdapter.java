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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.company.ptplatform.Model.Beans.Banner.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.BannerFragment;

import java.util.List;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private String flag;
    private LayoutInflater inflater;
    private List<Datum> listPic;

    public SliderAdapter(List<Datum> listPic, Activity context, String flag) {
        this.flag=flag;
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
        View SliderLayout = inflater.inflate(R.layout.layout_slider, container, false);
        assert SliderLayout != null;

        ImageView img = SliderLayout.findViewById(R.id.slider_img);
        Glide.with(context).load(listPic.get(position).getImage().toString()).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPic.get(position).getUrl() != null){
                    try {
                        Uri uri = Uri.parse(listPic.get(position).getUrl()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    } catch (Exception ignored){}
                } else {
                    setFragment(new BannerFragment(flag));
                }
            }
        });

        container.addView(SliderLayout, 0);
        return SliderLayout;
    }

    private void setFragment(Fragment fragment) {

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }

}
