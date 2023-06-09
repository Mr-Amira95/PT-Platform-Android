package com.company.ptplatform.View.Dialogs;

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

import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.General;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.ProgressFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


public class AddProgressDialog extends Dialog  implements CallBack {


    Context mContext;

    EditText weightEt,muscleEt,fatEt,waterEt,activeCaloriesEt,stepsEt;
    Button updateBtn;

    public AddProgressDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_progress);

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
    if(validation(weightEt)&&validation(muscleEt)
            &&validation(fatEt)&&validation(waterEt)
            &&validation(activeCaloriesEt)&&validation(stepsEt)) {
        params.put("weight", weightEt.getText().toString());
        params.put("muscle", muscleEt.getText().toString());
        params.put("fat", fatEt.getText().toString());
        params.put("water", waterEt.getText().toString());
        params.put("active_calories", activeCaloriesEt.getText().toString());
        params.put("steps", stepsEt.getText().toString());
        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.HEALTHS_URL, AppConstants.HEALTHS_TAG, General.class, params);
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
        weightEt=findViewById(R.id.weight_et);
        muscleEt=findViewById(R.id.muscle_et);
        fatEt=findViewById(R.id.fat_et);
        waterEt=findViewById(R.id.water_et);
        activeCaloriesEt=findViewById(R.id.active_calories_et);
        stepsEt=findViewById(R.id.steps_et);
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
            Toast.makeText(mContext, R.string.progress_added, Toast.LENGTH_SHORT).show();
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
