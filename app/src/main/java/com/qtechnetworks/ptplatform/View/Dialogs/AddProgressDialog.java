package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachesAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Coach;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.Progress.Progress;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;

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


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        if(isSuccess &&tag==AppConstants.HEALTHS_TAG){}
        Toast.makeText(mContext, "Progress Added", Toast.LENGTH_SHORT).show();
    this.dismiss();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
