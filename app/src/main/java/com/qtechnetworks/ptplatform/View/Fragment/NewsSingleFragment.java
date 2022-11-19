package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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

    String image;
    String title;
    String decription;

    ImageView news_img, share;
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
        clicks();

        return view;
    }

    private void clicks() {

        share.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("*/*");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, decription);
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, image);
//                startActivity(sharingIntent);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Text" + Html.fromHtml(decription));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
    }

    private void initial(View v){

        news_img=v.findViewById(R.id.news_img);
        title_text=v.findViewById(R.id.title_text);
        news_details=v.findViewById(R.id.news_details);
        share = v.findViewById(R.id.share_icon);

        try{
            Glide.with(getContext()).load(image).placeholder(R.drawable.logo).into(news_img);
        }catch (Exception e){
            e.printStackTrace();
        }

        title_text.setText(title);
        news_details.setText(Html.fromHtml(decription));

    }


}