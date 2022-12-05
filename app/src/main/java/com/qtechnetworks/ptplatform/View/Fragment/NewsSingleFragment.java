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
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.Beans.News.SingleNews;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class NewsSingleFragment extends Fragment implements CallBack {

    String image;
    String title;
    String decription;

    ImageView news_img, share;
    TextView title_text,news_details;

    String id;

    public NewsSingleFragment(String id) {
        this.id = id;
    }

    public NewsSingleFragment() { }

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
                if (title!= null)
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);

                if (decription != null)
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "News: " + Html.fromHtml(decription));

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

        if (id != null){

            getNews();

        } else {

            try{
                Glide.with(getContext()).load(image).placeholder(R.drawable.logo).into(news_img);
            }catch (Exception e){
                e.printStackTrace();
            }

            title_text.setText(title);
            news_details.setText(Html.fromHtml(decription));

        }

    }

    private void getNews() {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.news_URL+"/"+id+"/show", AppConstants.news_TAG, SingleNews.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        SingleNews singleNews = (SingleNews) result;

        try{
            Glide.with(getContext()).load(singleNews.getData().getImage()).placeholder(R.drawable.logo).into(news_img);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (singleNews.getData().getTitle() != null){
            title = singleNews.getData().getTitle();
            title_text.setText(title);

        }

        if (singleNews.getData().getDescription() != null){
            decription = singleNews.getData().getDescription();
            news_details.setText(Html.fromHtml(decription));
        }

    }

    @Override
    public void onError (Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}