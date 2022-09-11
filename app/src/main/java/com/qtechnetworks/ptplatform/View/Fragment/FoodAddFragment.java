package com.qtechnetworks.ptplatform.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Food;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FoodAddFragment extends Fragment implements CallBack {

    PieChart pieChart;
    String flag;
    ImageView doneIcon;
    Spinner Foodname_spinner;
    ArrayList<String> arrayListfood;

    TextView fat_text, carb_text, protine_text;

    EditText weightnumber_edit;

    String foodid;

    Food food;

    public FoodAddFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_add, container, false);

        initials(view);

        doneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood(foodid, flag, weightnumber_edit.getText().toString());
            }
        });

        setData();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        pieChart = view.findViewById(R.id.pie_chart);
        doneIcon = view.findViewById(R.id.done_icon);

        fat_text = view.findViewById(R.id.fat_text);
        carb_text = view.findViewById(R.id.carb_text);
        protine_text = view.findViewById(R.id.protine_text);

        Foodname_spinner = view.findViewById(R.id.Foodname_spinner);
        weightnumber_edit = view.findViewById(R.id.weightnumber_edit);

        arrayListfood = new ArrayList<>();

        getFood("0");

        Foodname_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                int carb=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getCarb();
                int fat=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getFat();
                int protein=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getProtein();
                fat_text.setText(fat+"");
                carb_text.setText(carb+"");
                protine_text.setText(protein+"");
                try {
                    pieChart.clearChart();
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Carbs",
                                    carb,
                                    Color.parseColor("#1EB1FC")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Fat",
                                    fat,
                                    Color.parseColor("#FF0000")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Protein",
                                    protein,
                                    Color.parseColor("#8DC63F")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "",
                                    100-carb-fat-protein,
                                    Color.parseColor("#FFFFFFFF")));
                }catch (Exception e){
                    e.printStackTrace();
                }
                //pieChart.addPieSlice(new PieModel("Carbs", 4, R.color.blue));
                //pieChart.addPieSlice(new PieModel("Fat", 16, R.color.red));
                //pieChart.addPieSlice(new PieModel("Protein", 80, R.color.primary_color));

                // To animate the pie chart
                pieChart.startAnimation();
                /*fat_text.setText(food.getData().get(position).getFat().toString());
                carb_text.setText(food.getData().get(position).getCarb().toString());
                protine_text.setText(food.getData().get(position).getProtein().toString());*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }





    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment).addToBackStack(null).commit();
    }

    private void setData() {

        // Set the percentage of language used
        // Set the data and color to the pie chart

    }

    private void getFood(String skip){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip",skip);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.food_URL, AppConstants.food_TAG, Food.class, params);

    }

    private void addFood(String foodid,String type,String number){

        HashMap<String ,Object> params=new HashMap<>();
        foodid=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getId().toString();
        params.put("food_id",foodid);
        params.put("type",type);
        params.put("number",number);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.addfood_URL, AppConstants.addfood_TAG, General.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {



        switch (tag){
            case AppConstants.food_TAG:
                food=(Food) result;
                for (int i=0;food.getData().size()>i;i++){
                    arrayListfood.add(food.getData().get(i).getName().toString());
                }

                UtilisMethods.fillSpinnerData(getContext(),arrayListfood,Foodname_spinner);

//                foodid=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getId().toString();
//
//
//                fat_text.setText(food.getData().get(Foodname_spinner.getSelectedItemPosition()).getFat().toString());
//                carb_text.setText(food.getData().get(Foodname_spinner.getSelectedItemPosition()).getCarb().toString());
//                protine_text.setText(food.getData().get(Foodname_spinner.getSelectedItemPosition()).getProtein().toString());


                break;

            case AppConstants.addfood_TAG:

                General general=(General) result;

                Toast.makeText(getContext(),general.getData().toString(),Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager().popBackStack();

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