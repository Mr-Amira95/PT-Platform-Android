package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;


public class AddLogDialog extends Dialog {

    Context context;
    Button save_btn;
    ExercisesSingleFragment exercisesSingleFragment;

    EditText weight,repititions,note;

    Spinner set_number,weight_unit;

    String [] number={"1","2","3","4","5","6","7","8","9","10"};
    String [] weightspi={"Pounds (lb)","Kilograms (kg)","Stones (st)"};

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

                dismiss();

                exercisesSingleFragment.addToLog(
                        exercisesSingleFragment.VideoID,
                        set_number.getSelectedItem().toString(),
                        weight.getText().toString(),
                        weight_unit.getSelectedItem().toString(),
                        repititions.getText().toString(),
                        note.getText().toString()
                );

            }
        });

    }

}
