package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.SupplementsAndDietPlansAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercises;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Food;
import com.qtechnetworks.ptplatform.Model.Beans.Supplement.Supplement;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class SupplementsDietPlansFragment extends Fragment implements CallBack {

    EditText searchBar;
    TextView title;
    RecyclerView recyclerView;
    SupplementsAndDietPlansAdapter adapter;
    String flag;

    public SupplementsDietPlansFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplements_diet_plan, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        title = view.findViewById(R.id.title);
        title.setText(flag);

        searchBar = view.findViewById(R.id.search_bar);
        if(flag.equals("Supplements")){

            searchBar.setHint("SEARCH FOR SUPLEMENTS");
            getSupplement();

        }else if (flag.equals("Recipes and Diet Plans")) {
            searchBar.setHint("SEARCH FOR RECIPES");


        }


        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


    }

    private void getSupplement(){

        HashMap<String ,Object> params=new HashMap<>();


        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Supplement_URL, AppConstants.Supplement_TAG, Supplement.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){

            case AppConstants.Supplement_TAG:

                Supplement supplement=(Supplement) result;

                adapter = new SupplementsAndDietPlansAdapter(getContext(), flag,supplement.getData());
                recyclerView.setAdapter(adapter);

                break;
        }


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}