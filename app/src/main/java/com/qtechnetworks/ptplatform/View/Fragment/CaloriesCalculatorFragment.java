package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.skydoves.powerspinner.PowerSpinnerView;

public class CaloriesCalculatorFragment extends Fragment {

    private Button calculateBtn;
    RadioButton male,female;
    EditText ageEdittext,heightEdittext,weightEdittext;
    TextView result;
    boolean isMale = true;

    PowerSpinnerView powerSpinnerView;

    double activityFactor=1.25;
    //Men: (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
    //Women: (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
    //
//    Sedentary (little or no exercise): calories = BMR × 1.2;
//    Lightly active (light exercise/sports 1-3 days/week): calories = BMR × 1.375;
//    Moderately active (moderate exercise/sports 3-5 days/week): calories = BMR × 1.55;
//    Very active (hard exercise/sports 6-7 days a week): calories = BMR × 1.725; and
//    If you are extra active (very hard exercise/sports & a physical job): calories = BMR × 1.9.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories_calculator, container, false);
        initials(view);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMale=true;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMale=false;
            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!weightEdittext.getText().toString().isEmpty() && !heightEdittext.getText().toString().isEmpty() && powerSpinnerView.getSelectedIndex() >= 0){

                    closeKeyboard();
                    double weight=Double.parseDouble(weightEdittext.getText().toString());
                    double height=Double.parseDouble(heightEdittext.getText().toString());
                    double age=Integer.parseInt(ageEdittext.getText().toString());

                    switch (powerSpinnerView.getSelectedIndex()){
                        case 0:
                            activityFactor=1.25;
                            break;
                        case 1:
                            activityFactor=1.375;
                            break;
                        case 2:
                            activityFactor=1.55;
                            break;
                        case 3:
                            activityFactor=1.725;
                            break;
                    }

                    if(isMale) {
                        double value = ((66.5 + (13.7 * weight) + (5 * height) - (6.8 * age)) * activityFactor);
                        result.setText(value +" calories/day");
                    } else {
                        double value =((665.5 + (9.6 * weight) + (1.8 * height) - (4.7 * age)) * activityFactor);
                        result.setText(value +" calories/day");
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill al the above data", Toast.LENGTH_SHORT).show();
                }
               // setFragment(R.id.home_frame, new NutritionFragment());

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        calculateBtn = view.findViewById(R.id.calculate_btn);
        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        heightEdittext=view.findViewById(R.id.height_edittext);
        ageEdittext=view.findViewById(R.id.age_edittext);
        weightEdittext=view.findViewById(R.id.weight_edittext);
        result=view.findViewById(R.id.result);
        powerSpinnerView=view.findViewById(R.id.activity_spinner);
    }

    private void closeKeyboard() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }
    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}