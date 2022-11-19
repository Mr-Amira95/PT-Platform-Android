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
import com.qtechnetworks.ptplatform.View.Dialogs.FoodDialog;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FoodAddFragment extends Fragment implements CallBack {

    String flag;
    ImageView doneIcon;

    public static TextView fat_text, carb_text, protine_text, calories_text, Foodname_spinner;
    TextView increase, decrease;
    public static EditText weightnumber_edit;

    FoodDialog foodDialog;

    public static int selectedFoodIndex = -1;

    public FoodAddFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_add, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        doneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFoodIndex != -1) {
                    addFood();
                } else {
                    Toast.makeText(getContext(), getString(R.string.please_select_food), Toast.LENGTH_SHORT).show();
                    foodDialog = new FoodDialog(getContext());
                    foodDialog.show();
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt(weightnumber_edit.getText().toString())-1;
                if (i>0 && selectedFoodIndex != -1){
                    weightnumber_edit.setText(String.valueOf(i));

                    double carb= FoodDialog.food.getData().get(selectedFoodIndex).getCarb() * i;
                    double fat=FoodDialog.food.getData().get(selectedFoodIndex).getFat() * i;
                    double protein=FoodDialog.food.getData().get(selectedFoodIndex).getProtein() * i;
                    int calories=FoodDialog.food.getData().get(selectedFoodIndex).getCalorie() * i;
                    fat_text.setText(String.valueOf(fat));
                    carb_text.setText(String.valueOf(carb));
                    protine_text.setText(String.valueOf(protein));
                    calories_text.setText(String.valueOf(calories));

                } else if (selectedFoodIndex == -1) {
                    Toast.makeText(getContext(), R.string.please_select_food, Toast.LENGTH_SHORT).show();
                    foodDialog = new FoodDialog(getContext());
                    foodDialog.show();
                }
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedFoodIndex != -1) {
                    int i = Integer.parseInt(weightnumber_edit.getText().toString()) + 1;
                    weightnumber_edit.setText(String.valueOf(i));

                    double carb = FoodDialog.food.getData().get(selectedFoodIndex).getCarb() * i;
                    double fat = FoodDialog.food.getData().get(selectedFoodIndex).getFat() * i;
                    double protein = FoodDialog.food.getData().get(selectedFoodIndex).getProtein() * i;
                    int calories = FoodDialog.food.getData().get(selectedFoodIndex).getCalorie() * i;
                    fat_text.setText(String.valueOf(fat));
                    carb_text.setText(String.valueOf(carb));
                    protine_text.setText(String.valueOf(protein));
                    calories_text.setText(String.valueOf(calories));
                } else {
                    Toast.makeText(getContext(), getString(R.string.please_select_food), Toast.LENGTH_SHORT).show();
                    foodDialog = new FoodDialog(getContext());
                    foodDialog.show();
                }
            }
        });

    }

    private void initials(View view) {
        calories_text = view.findViewById(R.id.calories_text);
        doneIcon = view.findViewById(R.id.done_icon);
        increase = view.findViewById(R.id.increase);
        decrease = view.findViewById(R.id.decrease);

        fat_text = view.findViewById(R.id.fat_text);
        carb_text = view.findViewById(R.id.carb_text);
        protine_text = view.findViewById(R.id.protine_text);

        Foodname_spinner = view.findViewById(R.id.Foodname_spinner);
        weightnumber_edit = view.findViewById(R.id.weightnumber_edit);

        foodDialog = new FoodDialog(getContext());
        foodDialog.show();

        Foodname_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodDialog = new FoodDialog(getContext());
                foodDialog.show();
            }
        });


/*
        Foodname_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                int carb=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getCarb() * Integer.parseInt(weightnumber_edit.getText().toString());
                int fat=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getFat() * Integer.parseInt(weightnumber_edit.getText().toString());
                int protein=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getProtein() * Integer.parseInt(weightnumber_edit.getText().toString());
                int calories=food.getData().get(Foodname_spinner.getSelectedItemPosition()).getCalorie() * Integer.parseInt(weightnumber_edit.getText().toString());
                fat_text.setText(fat+"");
                carb_text.setText(carb+"");
                protine_text.setText(protein+"");
                calories_text.setText(calories+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
*/
    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment).addToBackStack(null).commit();
    }


    private void addFood() {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("food_id", FoodDialog.food.getData().get(selectedFoodIndex).getId());
        params.put("type",flag);
        params.put("number",weightnumber_edit.getText().toString());

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