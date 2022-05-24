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

public class SupplementSingleFragment extends Fragment {

    String image,title,descrip;

    ImageView supplement_img;

    TextView description_text,title_text_pa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image=getArguments().getString("image");
            title=getArguments().getString("title");
            descrip=getArguments().getString("descrip");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplement_single, container, false);
        // Inflate the layout for this fragment

        initial(view);

        return view;
    }

    private void initial(View v){

        supplement_img=v.findViewById(R.id.supplement_img);
        description_text=v.findViewById(R.id.description_text);
        title_text_pa=v.findViewById(R.id.title_text_pa);

        title_text_pa.setText(title);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            description_text.setText(Html.fromHtml(descrip,Html.FROM_HTML_MODE_LEGACY));
        } else {
            description_text.setText(Html.fromHtml(descrip));
        }


        try{

            Glide.with(getContext()).load(image).placeholder(R.drawable.logo).into(supplement_img);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}