package com.qtechnetworks.ptplatform.View.Activity;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Controller.adapters.CategoryAdapter;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity {

    Slider slider;

    ArrayList<Integer> url;

    ImageView background_imageview;

    RecyclerView main_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();

    }

    private  void initial() {

        main_recyclerview=findViewById(R.id.main_recyclerview);

        background_imageview=findViewById(R.id.background_imageview);

        Glide.with(getApplicationContext()).load(R.drawable.demoslider)
                .apply(bitmapTransform(new BlurTransformation(190,2)))
                .into((ImageView) findViewById(R.id.background_imageview));

        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                // TODO: Handle the connection...
                if (isConnected){
                    slider = findViewById(R.id.banner_slider1);
                    url=new ArrayList<Integer>();
                    url.add(R.drawable.demoslider);
                    Slider.init(new PicassoImageLoadingService(getApplicationContext()));

                    fillrecycler();

                }else {

                    Toast.makeText(getApplicationContext(),"Check Internet connection",Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    private void fillrecycler(){

         List<String> list = new ArrayList<String>() {{
            add("Follow Us");
            add("Coaches");
            add("Contact Us");
            add("News");
        }};

        List<Integer> listpic = new ArrayList<Integer>() {{
            add(R.drawable.follwous);
            add(R.drawable.coaches);
            add(R.drawable.contactus);
            add(R.drawable.follwous);
        }};

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getApplicationContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.HORIZONTAL);

        main_recyclerview.setLayoutManager(layoutManagerhorizantalleader);

        CategoryAdapter categoryAdapter = new CategoryAdapter(list,listpic , getApplicationContext());
        main_recyclerview.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    public class PicassoImageLoadingService implements ImageLoadingService {
        public Context context;

        public PicassoImageLoadingService(Context context) {
            this.context = context;
        }

        @Override
        public void loadImage(String url, ImageView imageView) {

            try {
                Glide.with(context).load(url).into(imageView);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            try {
                Glide.with(context).load(url).into(imageView);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {

            try {
                Glide.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStop() {
        Tovuti.from(this).stop();
        super.onStop();
    }
}