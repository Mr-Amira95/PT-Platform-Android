
package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement.BodyMeasurementSingle;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ProgressFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

    public class AddMeasurementsDialog extends Dialog implements CallBack {

        Context mContext;

        EditText neckEt,chestEt,leftArmEt,rightArmEt,waistEt,bellyEt,bellyUpperEt,bellyLowerEt,hipsEt,
                leftThighEt,rightThighEt,leftCalfEt,rightClafEt;
        Button updateBtn;

        public AddMeasurementsDialog(@NonNull Context context) {
            super(context);
            this.mContext = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_add_measurments);

            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            initials();
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update();
                }
            });

        }
        private void update(){
            HashMap<String ,Object> params=new HashMap<>();
            if(validation(neckEt)&&validation(chestEt)
                    &&validation(waistEt)&&validation(bellyEt)
                    &&validation(hipsEt)&&validation(leftThighEt)
                    &&validation(rightThighEt)&&validation(leftCalfEt)
                    &&validation(rightClafEt) &&validation(leftArmEt)&&validation(rightArmEt)) {
//                "neck" : 1.4,
//                        "chest" : 1.4,
//                        "left_arm" : 1.4,
//                        "right_arm" : 1.4,
//                        "waist" : 1.6,
//                        "belly" : 1.2,
//                        "hips" : 1.2,
//                        "left_thigh" : 1.2,
//                        "right_thigh" : 1.2,
//                        "lift_calf" : 1.2,
//                        "right_calf" : 1.2
       //         params.put("date", dateEt.getText().toString());
             //   params.put("data", dateEt.getText().toString());
                params.put("neck", neckEt.getText().toString());
                params.put("chest", chestEt.getText().toString());
                params.put("left_arm", leftArmEt.getText().toString());
                params.put("right_arm", rightArmEt.getText().toString());
                params.put("waist", waistEt.getText().toString());
                params.put("belly", bellyEt.getText().toString());
                params.put("lower_belly", bellyLowerEt.getText().toString());
                params.put("upper_belly", bellyUpperEt.getText().toString());
                params.put("hips", hipsEt.getText().toString());
                params.put("left_thigh", leftThighEt.getText().toString());
                params.put("right_thigh", rightThighEt.getText().toString());
                params.put("lift_calf", leftCalfEt.getText().toString());
                params.put("right_calf", rightClafEt.getText().toString());
                MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
                MyApplication.getInstance().getBackgroundHttpHelper().Post(getContext(), AppConstants.BODYMAESUREMENTS_URL, AppConstants.BODYMAESUREMENTS_TAG, BodyMeasurementSingle.class, params);
            }
        }
        private boolean validation(EditText et){
            if( TextUtils.isEmpty(et.getText())){
                et.setError( "this field is required!" );
                return false;
            }
            return true;
        }
        private void initials() {
          //  dateEt=findViewById(R.id.date_et);
            neckEt=findViewById(R.id.neck_et);
            chestEt=findViewById(R.id.chest_et);
            rightArmEt=findViewById(R.id.right_arm_et);
            leftArmEt=findViewById(R.id.left_arm_et);
            waistEt=findViewById(R.id.waist_et);
            bellyEt=findViewById(R.id.belly_et);
            bellyLowerEt=findViewById(R.id.belly_lower_et);
            bellyUpperEt=findViewById(R.id.belly_upper_et);
            hipsEt=findViewById(R.id.hips_et);
            leftThighEt=findViewById(R.id.left_thigh_et);
            rightThighEt=findViewById(R.id.right_thigh_et);
            leftCalfEt=findViewById(R.id.left_calf_et);
            rightClafEt=findViewById(R.id.right_calf_et);

            updateBtn=findViewById(R.id.update_btn);
        }

        private void setFragment(Fragment fragment) {

            Bundle args = new Bundle();
            fragment.setArguments(args);

            ((MainActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

        }
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(int tag, boolean isSuccess, Object result) {
            if(isSuccess) {
                Toast.makeText(mContext, R.string.measurments_added, Toast.LENGTH_SHORT).show();
                setFragment(new ProgressFragment());
            }
            this.dismiss();

        }
        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
