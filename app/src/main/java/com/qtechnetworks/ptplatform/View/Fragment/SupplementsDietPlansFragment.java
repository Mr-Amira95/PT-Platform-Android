package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.RecipesAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.SupplementsAndDietPlansAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Recipes.Recipes;
import com.qtechnetworks.ptplatform.Model.Beans.Supplement.Supplement;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class SupplementsDietPlansFragment extends Fragment implements CallBack{

    EditText searchBar;
    TextView title;
    RecyclerView recyclerView;
    Button searchBtn;
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
        searchBtn = view.findViewById(R.id.search_button);

        searchBar = view.findViewById(R.id.search_bar);

        if(flag.equals("Supplements")){

            searchBar.setHint(getString(R.string.search_for_suplements));
            getSupplement();
            title.setText(R.string.supplements);

        } else if (flag.equals("Recipes and Diet Plans")) {
            searchBar.setHint(R.string.search_for_recipes);
            title.setText(R.string.recipes_and_diet_plans);
            getRecipes();

        }


        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (flag.equals("Supplements")){
                        searchSupplement (searchBar.getText().toString());
                    } else if (flag.equals("Recipes and Diet Plans")) {
                        searchRecipes(searchBar.getText().toString());
                    }
            }
        });

    }

    private void getRecipes(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.recipes_URL, AppConstants.recipes_TAG, Recipes.class, params);

    }

    private void getSupplement(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Supplement_URL, AppConstants.Supplement_TAG, Supplement.class, params);

    }

    private void searchRecipes(String word){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");
        params.put("name",word);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.recipes_URL, 98, Recipes.class, params);

    }

    private void searchSupplement(String word){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("title",word);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Supplement_URL, 99, Supplement.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){

            case AppConstants.Supplement_TAG:

                Supplement supplement=(Supplement) result;

                SupplementsAndDietPlansAdapter adapter = new SupplementsAndDietPlansAdapter(getContext(), flag,supplement.getData());
                recyclerView.setAdapter(adapter);

                break;

            case AppConstants.recipes_TAG:

                Recipes recipes=(Recipes) result;

                RecipesAdapter recipesAdapter=new RecipesAdapter(getContext(),flag,recipes.getData());
                recyclerView.setAdapter(recipesAdapter);

                break;

            case 98:

                Recipes recipes1=(Recipes) result;

                RecipesAdapter recipesAdapter1=new RecipesAdapter(getContext(),flag, recipes1.getData());
                recyclerView.setAdapter(recipesAdapter1);

                break;

            case 99:

                Supplement supplement1=(Supplement) result;

                SupplementsAndDietPlansAdapter adapter1 = new SupplementsAndDietPlansAdapter(getContext(), flag,supplement1.getData());
                recyclerView.setAdapter(adapter1);

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