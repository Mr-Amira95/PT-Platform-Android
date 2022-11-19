package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement.BodyMeasurement;
import com.qtechnetworks.ptplatform.Model.Beans.Progress.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.Progress.Percentage;
import com.qtechnetworks.ptplatform.Model.Beans.Progress.Progress;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.AddMeasurementsDialog;
import com.qtechnetworks.ptplatform.View.Dialogs.AddProgressDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ProgressFragment extends Fragment  implements CallBack {

    private TextView weightTitle, weightValue, muscleTitle, muscleValue, fatTitle, fatValue, waterTitle, waterValue, activeCaloriesTitle, activeCaloriesValue, stepsTitle, stepsValue,
            beforeDate,afterDate,beforeNick,afterNick,beforeChest,afterChest,beforeLeftArm,afterLeftArm,
            beforeRightArm,afterRightArm,beforeWaist,afterWaist,beforeHips,afterHips,
            beforeLeftThigh,afterLeftThigh,beforeLeftCalf,afterLeftCalf,beforeRightCalf,
            afterRightCalf,beforeBelly,afterBelly,beforeUpperBelly,afterUpperBelly,beforelowerBelly,
            afterlowerBelly,fatPercentage,musclePercentae,weightPercentage,waterPercentage;

    private ImageView fatIncrease, fatDecrease, muscleIncrease,muscleDecrease,weightIncrease,weightDecrease,waterIncrease,waterDecrease;

    private Button updateProgressBtn,updateMeasurementsBtn;

    private AddProgressDialog progressDialog;
    private AddMeasurementsDialog maesurementsDialog;

    Integer userID;

    public ProgressFragment(Integer userID) {
        this.userID = userID;
    }

    public ProgressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        weightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weightValue.getVisibility() == View.GONE)
                    weightValue.setVisibility(View.VISIBLE);
                else
                    weightValue.setVisibility(View.GONE);
            }
        });

        muscleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (muscleValue.getVisibility() == View.GONE)
                    muscleValue.setVisibility(View.VISIBLE);
                else
                    muscleValue.setVisibility(View.GONE);
            }
        });

        fatTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fatValue.getVisibility() == View.GONE)
                    fatValue.setVisibility(View.VISIBLE);
                else
                    fatValue.setVisibility(View.GONE);
            }
        });

        waterTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterValue.getVisibility() == View.GONE)
                    waterValue.setVisibility(View.VISIBLE);
                else
                    waterValue.setVisibility(View.GONE);
            }
        });

        activeCaloriesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeCaloriesValue.getVisibility() == View.GONE)
                    activeCaloriesValue.setVisibility(View.VISIBLE);
                else
                    activeCaloriesValue.setVisibility(View.GONE);
            }
        });

        stepsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsValue.getVisibility() == View.GONE)
                    stepsValue.setVisibility(View.VISIBLE);
                else
                    stepsValue.setVisibility(View.GONE);
            }
        });

        updateProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new AddProgressDialog(getContext());
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
//                        getPrrogress();
                    }
                });
            }
        });

        updateMeasurementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maesurementsDialog = new AddMeasurementsDialog(getContext());
                maesurementsDialog.setCancelable(true);
                maesurementsDialog.show();
                maesurementsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

//                        getMeasurements();
                    }
                });
            }
        });
    }

    private void initials(View view) {
        weightTitle = view.findViewById(R.id.weight_title);
        weightValue = view.findViewById(R.id.weight_value);
        muscleTitle = view.findViewById(R.id.muscle_title);
        muscleValue = view.findViewById(R.id.muscle_value);
        fatTitle = view.findViewById(R.id.fat_title);
        fatValue = view.findViewById(R.id.fat_value);
        waterTitle = view.findViewById(R.id.water_title);
        waterValue = view.findViewById(R.id.water_value);
        activeCaloriesTitle = view.findViewById(R.id.active_calories_title);
        activeCaloriesValue = view.findViewById(R.id.active_calories_value);
        stepsTitle = view.findViewById(R.id.steps_title);
        stepsValue = view.findViewById(R.id.steps_value);

        updateProgressBtn=view.findViewById(R.id.update_progress_btn);
        updateMeasurementsBtn=view.findViewById(R.id.update_measurements_btn);
        beforeDate=view.findViewById(R.id.date);
        afterDate=view.findViewById(R.id.date_after);
        beforeNick=view.findViewById(R.id.neck);
        afterNick=view.findViewById(R.id.neck_after);
        beforeChest=view.findViewById(R.id.chest);
        afterChest=view.findViewById(R.id.chest_after);
        beforeLeftArm=view.findViewById(R.id.left_arm);
        afterLeftArm=view.findViewById(R.id.left_arm_after);
        beforeRightArm=view.findViewById(R.id.right_arm);
        afterRightArm=view.findViewById(R.id.right_arm_after);
        beforeWaist=view.findViewById(R.id.waist);
        afterWaist=view.findViewById(R.id.waist_after);
        beforeHips=view.findViewById(R.id.hips);
        afterHips=view.findViewById(R.id.hips_after);
        beforeLeftThigh=view.findViewById(R.id.left_thigh);
        afterLeftThigh=view.findViewById(R.id.left_thigh_after);
        beforeLeftCalf=view.findViewById(R.id.left_calf);
        afterLeftCalf=view.findViewById(R.id.left_calf_after);
        beforeRightCalf=view.findViewById(R.id.right_calf);
        afterRightCalf=view.findViewById(R.id.right_calf_after);
        beforeBelly=view.findViewById(R.id.belly);
        afterBelly=view.findViewById(R.id.belly_after);
        beforeUpperBelly=view.findViewById(R.id.upper_belly);
        afterUpperBelly=view.findViewById(R.id.upper_belly_after);
        beforelowerBelly=view.findViewById(R.id.lower_belly);
        afterlowerBelly=view.findViewById(R.id.lower_belly_after);

        fatPercentage=view.findViewById(R.id.fat_percentage);
        musclePercentae=view.findViewById(R.id.muscle_percentage);
        weightPercentage=view.findViewById(R.id.weight_percentage);
        waterPercentage=view.findViewById(R.id.water_percentage);

        fatDecrease=view.findViewById(R.id.fat_decrease_iv);
        fatIncrease=view.findViewById(R.id.fat_increase_iv);
        muscleDecrease=view.findViewById(R.id.muscle_decrease_iv);
        muscleIncrease=view.findViewById(R.id.muscle_increase_iv);
        weightDecrease=view.findViewById(R.id.weight_decrease_iv);
        weightIncrease=view.findViewById(R.id.weight_increase_iv);
        waterIncrease=view.findViewById(R.id.water_increase_iv);
        waterDecrease=view.findViewById(R.id.water_decrease_iv);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")) {
            getPrrogressCoach();
            getMeasurementsCoach();
            updateProgressBtn.setVisibility(View.GONE);
            updateMeasurementsBtn.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
            getPrrogress();
            getMeasurements();
            updateProgressBtn.setVisibility(View.VISIBLE);
            updateMeasurementsBtn.setVisibility(View.VISIBLE);
        }

    }

    private void getMeasurements(){
        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get((MainActivity) getActivity(), AppConstants.BODYMAESUREMENTS_URL, AppConstants.BODYMAESUREMENTS_TAG, BodyMeasurement.class, params);
    }

    private void getPrrogress(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getActivity(), AppConstants.HEALTHS_URL, AppConstants.HEALTHS_TAG, Progress.class, params);

    }

    private void getMeasurementsCoach(){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", userID);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get((MainActivity) getActivity(), AppConstants.BODYMAESUREMENTS_URL, AppConstants.BODYMAESUREMENTS_TAG, BodyMeasurement.class, params);
    }

    private void getPrrogressCoach(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", userID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getActivity(), AppConstants.HEALTHS_URL, AppConstants.HEALTHS_TAG, Progress.class, params);

    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if(isSuccess){
            switch (tag){
                case AppConstants.HEALTHS_TAG:

                    Progress myProgress=(Progress) result;

                    for (Datum myp :myProgress.getData()) {
                        if(myp.getFat()!=null){
                            fatValue.setText(myp.getFat().toString()+"%");
                            Percentage percentage=myp.getPercentage();
                            fatPercentage.setText(percentage.getValue().toString()+"%");
                            if(percentage.getType().equals("decrease")){
                                fatDecrease.setVisibility(View.VISIBLE);
                                fatIncrease.setVisibility(View.GONE);
                            }else{
                                fatDecrease.setVisibility(View.GONE);
                                fatIncrease.setVisibility(View.VISIBLE);
                            }
                        }

                        if(myp.getMuscle()!=null){
                            muscleValue.setText(myp.getMuscle().toString()+"%");
                            Percentage percentage=myp.getPercentage();
                            musclePercentae.setText(percentage.getValue().toString()+"%");
                            if(percentage.getType().equals("decrease")){
                                muscleDecrease.setVisibility(View.VISIBLE);
                                muscleIncrease.setVisibility(View.GONE);
                            }else{
                                muscleDecrease.setVisibility(View.GONE);
                                muscleIncrease.setVisibility(View.VISIBLE);
                            }
                        }

                        if(myp.getWeight()!=null){
                            weightValue.setText(myp.getWeight().toString()+"kg");
                            Percentage percentage=myp.getPercentage();
                            weightPercentage.setText(percentage.getValue().toString()+"%");
                            if(percentage.getType().equals("decrease")){
                                weightDecrease.setVisibility(View.VISIBLE);
                                weightIncrease.setVisibility(View.GONE);
                            }else{
                                weightDecrease.setVisibility(View.GONE);
                                weightIncrease.setVisibility(View.VISIBLE);
                            }
                        }

                        if(myp.getWater()!=null){
                            waterValue.setText(myp.getWater().toString()+"%");
                            Percentage percentage=myp.getPercentage();
                            waterPercentage.setText(percentage.getValue().toString()+"%");
                            if(percentage.getType().equals("decrease")){
                                waterDecrease.setVisibility(View.VISIBLE);
                                waterIncrease.setVisibility(View.GONE);
                            }else{
                                waterDecrease.setVisibility(View.GONE);
                                waterIncrease.setVisibility(View.VISIBLE);
                            }
                        }

                        if(myp.getActive_calories()!=null){
                            activeCaloriesValue.setText(myp.getActive_calories().toString()+" " + getString(R.string.calories_1));
                            Percentage percentage=myp.getPercentage();
                            musclePercentae.setText(percentage.getValue().toString()+"%");

                        }

                        if(myp.getSteps()!=null){
                            stepsValue.setText(myp.getSteps().toString()+" Steps");
                            Percentage percentage=myp.getPercentage();
                            musclePercentae.setText(percentage.getValue().toString()+"%");
                        }
                    }

                    break;

                case AppConstants.BODYMAESUREMENTS_TAG:
                    BodyMeasurement bodyMeasurement=(BodyMeasurement) result;
                    if(bodyMeasurement.getData().size()==2) {
                        com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement.Datum beforeMeasurement=bodyMeasurement.getData().get(1);
                        com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement.Datum afterMeasurement=bodyMeasurement.getData().get(0);
                        if (beforeMeasurement!= null) {

                            //TODO Edit Names Like Nick
                            beforeNick.append(beforeMeasurement.getNeck().toString());
                            beforeChest.append(beforeMeasurement.getChest().toString());
                            beforeLeftArm.append(beforeMeasurement.getLeftArm().toString());
                            beforeRightArm.append(beforeMeasurement.getRightArm().toString());
                            beforeWaist.append(beforeMeasurement.getWaist().toString());
                            beforeHips.append(beforeMeasurement.getHips().toString());
                            beforeLeftThigh.append(beforeMeasurement.getLeftThigh().toString());
                            beforeLeftCalf.append(beforeMeasurement.getLiftCalf().toString());
                            beforeRightCalf.append(beforeMeasurement.getRightCalf().toString());
                            beforeDate.append(changeDateFormat(beforeMeasurement.getDate()));
                            beforeBelly.append(String.valueOf(beforeMeasurement.getBelly()));
                            beforeUpperBelly.append(String.valueOf(beforeMeasurement.getUpper_belly()));
                            beforelowerBelly.append(String.valueOf(beforeMeasurement.getLower_belly()));

                        }
                        if (afterMeasurement!= null) {
                            afterNick.append(afterMeasurement.getNeck().toString());
                            afterChest.append(afterMeasurement.getChest().toString());
                            afterLeftArm.append(afterMeasurement.getLeftArm().toString());
                            afterRightArm.append(afterMeasurement.getRightArm().toString());
                            afterWaist.append(afterMeasurement.getWaist().toString());
                            afterHips.append(afterMeasurement.getHips().toString());
                            afterLeftThigh.append(afterMeasurement.getLeftThigh().toString());
                            afterLeftCalf.append(afterMeasurement.getLiftCalf().toString());
                            afterRightCalf.append(afterMeasurement.getRightCalf().toString());
                            afterDate.append(changeDateFormat(afterMeasurement.getDate()));
                            afterBelly.append(String.valueOf(afterMeasurement.getBelly()));
                            afterUpperBelly.append(String.valueOf(afterMeasurement.getUpper_belly()));
                            afterlowerBelly.append(String.valueOf(afterMeasurement.getLower_belly()));
                        }
                    }

                    if(bodyMeasurement.getData().size()==1) {
                        com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement.Datum beforeMeasurement=bodyMeasurement.getData().get(0);
                        if (beforeMeasurement!= null) {
                            beforeNick.append(beforeMeasurement.getNeck().toString());
                            beforeChest.append(beforeMeasurement.getChest().toString());
                            beforeLeftArm.append(beforeMeasurement.getLeftArm().toString());
                            beforeRightArm.append(beforeMeasurement.getRightArm().toString());
                            beforeWaist.append(beforeMeasurement.getWaist().toString());
                            beforeHips.append(beforeMeasurement.getHips().toString());
                            beforeLeftThigh.append(beforeMeasurement.getLeftThigh().toString());
                            beforeLeftCalf.append(beforeMeasurement.getLiftCalf().toString());
                            beforeRightCalf.append(beforeMeasurement.getRightCalf().toString());
                            beforeDate.append(changeDateFormat(beforeMeasurement.getDate()));
                            beforeBelly.append(String.valueOf(beforeMeasurement.getBelly()));
                            beforeUpperBelly.append(String.valueOf(beforeMeasurement.getUpper_belly()));
                            beforelowerBelly.append(String.valueOf(beforeMeasurement.getLower_belly()));
                        }
                    }

                    break;
            }
        }
    }

    private String changeDateFormat(String unFormated) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "YYYY-MMM-dd";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat= new SimpleDateFormat(outputPattern);

            try {
                Date date = inputFormat.parse(unFormated);
                return outputFormat.format(date);
            } catch (Exception e){
                return unFormated;
            }
        } else {
            return unFormated;
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}