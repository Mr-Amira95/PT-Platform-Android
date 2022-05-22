package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.R;

public class NewsSingleFragment extends Fragment {

    String image,title,decription;

    ImageView news_img;
    TextView title_text,news_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getString("image");
            title = getArguments().getString("title");
            decription = getArguments().getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_single, container, false);

        // Inflate the layout for this fragment

        initial(view);

        return view;
    }

    private void initial(View v){

        news_img=v.findViewById(R.id.news_img);
        title_text=v.findViewById(R.id.title_text);
        news_details=v.findViewById(R.id.news_details);

        try{

            Glide.with(getContext()).load(image).placeholder(R.drawable.logo).into(news_img);

        }catch (Exception e){
            e.printStackTrace();
        }

        title_text.setText(title);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            news_details.setText(Html.fromHtml(decription,Html.FROM_HTML_MODE_LEGACY));
        } else {
            news_details.setText(Html.fromHtml(decription));
        }



    }


}