package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;

public class FollowUsFragment extends Fragment {

    Button facebookBtn,instagramBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_follow_us, container, false);
        init( view);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoUri = "https://web.facebook.com/profile.php?id=100083373336595";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoUri = "https://www.instagram.com/pt.platform.app/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void  init(View view){
        facebookBtn=view.findViewById(R.id.facebook_btn);
        instagramBtn=view.findViewById(R.id.instagram_btn);
        //https://web.facebook.com/profile.php?id=100083373336595
        //https://www.instagram.com/pt.platform.app/

    }
}