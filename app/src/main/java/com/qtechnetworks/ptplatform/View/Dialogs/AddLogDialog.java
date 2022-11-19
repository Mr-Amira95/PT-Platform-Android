package com.qtechnetworks.ptplatform.View.Dialogs;

import static com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment.*;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.addto.Adtofavlog;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;

import java.util.HashMap;
import java.util.Locale;

import io.reactivex.disposables.Disposable;


public class AddLogDialog extends Dialog implements CallBack {

    Context context;
    Button save_btn;
    ExercisesSingleFragment exercisesSingleFragment;

    EditText weight,repititions,note;
    Spinner set_number, weight_unit;

    int sets = 0;

    String [] number={"1","2","3","4","5","6","7","8","9","10"};
    String [] weightspi={"Pound","Kilogram","Stone"};

    public AddLogDialog(@NonNull Context context, ExercisesSingleFragment exercisesSingleFragment) {
        super(context);
        this.context = context;
        this.exercisesSingleFragment=exercisesSingleFragment;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_log);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();

    }


    private void initials() {

        save_btn=findViewById(R.id.save_btn);
        weight=findViewById(R.id.weight);
        repititions=findViewById(R.id.repititions);
        set_number=findViewById(R.id.set_number);
        weight_unit=findViewById(R.id.weight_unit);
        note=findViewById(R.id.note);

        UtilisMethods.fillSpinnerData(context,number,set_number);
        UtilisMethods.fillSpinnerData(context,weightspi,weight_unit);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sets == 0)
                    sets = Integer.parseInt(set_number.getSelectedItem().toString());

//                String weightUnit =" ";
//                switch (weight_unit.getSelectedItemPosition()){
//                    case 1:
//                        weightUnit = "kg";
//                        break;
//                    case 2:
//                        weightUnit = "st";
//                        break;
//                    default:
//                        weightUnit = "lb";
//                }

                addToLog();
            }
        });

    }

    public void addToLog(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("video_id",exercisesSingleFragment.VideoID);
        params.put("number","1");
        params.put("weight",weight.getText().toString());
        params.put("weight_unit", weight_unit.getSelectedItem().toString().toLowerCase(Locale.ROOT));
        params.put("repetition",repititions.getText().toString());
        params.put("note",note.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, Adtofavlog.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {
            Adtofavlog adtofavlog = (Adtofavlog) result;

            if (sets > 1){
                sets--;

                set_number.setEnabled(false);
                set_number.setClickable(false);

                weight_unit.setClickable(false);
                weight_unit.setEnabled(false);

                weight.setText("");
                repititions.setText("");
                note.setText("");

                Toast.makeText(context, "Please add the next set details" , Toast.LENGTH_SHORT).show();

            } else {
                dismiss();
//                if (adtofavlog.getData().getIsFavourite())
//                    add_to_favourite.setText("Remove from favourite");
//                else
//                    add_to_favourite.setText("Add to favourite");
//
//                if (adtofavlog.getData().getIsWorkout())
//                    add_to_workout.setText("Remove from Workout");
//                else
//                    add_to_workout.setText("Add to Workout");
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
