package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Controller.adapters.IngredientsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.Model.Beans.Recipes.Datum;
import com.qtechnetworks.ptplatform.R;

public class PlansSingleFragment extends Fragment {

    IngredientsAdapter ingredientsAdapter;
    RecyclerView ingredientsRecyclerview;
    Datum current;

    ImageView image_rec;
    TextView title_text,decrirtion_text,time_text,name_text;

    public PlansSingleFragment(Datum current) {
        this.current=current;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans_single, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        ingredientsRecyclerview = view.findViewById(R.id.ingredients_recyclerview);

        image_rec=view.findViewById(R.id.image_rec);
        title_text=view.findViewById(R.id.title_text);
        decrirtion_text=view.findViewById(R.id.decrirtion_text);
        time_text=view.findViewById(R.id.time_text);
        name_text=view.findViewById(R.id.name_text);

        title_text.setText(current.getTitle());
        time_text.setText(current.getTime().toString());
        name_text.setText(current.getName().toString());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            decrirtion_text.setText(Html.fromHtml(current.getDescription(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            decrirtion_text.setText(Html.fromHtml(current.getDescription()));
        }


        try{

            Glide.with(getContext()).load(current.getImage()).placeholder(R.drawable.logo).into(image_rec);

        }catch (Exception e){
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ingredientsRecyclerview.setLayoutManager(linearLayoutManager);

        ingredientsAdapter=new IngredientsAdapter(getContext(),"Recipe",current.getIngredients());
        ingredientsRecyclerview.setAdapter(ingredientsAdapter);


    }
}