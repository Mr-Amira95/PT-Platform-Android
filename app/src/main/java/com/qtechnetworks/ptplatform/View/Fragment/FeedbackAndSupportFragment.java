package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FeedbackAndSupportFragment extends Fragment implements CallBack {

    TextView title;
    String flag="";

    Button send_button;

    EditText name_edittext,message_edittext;

    public FeedbackAndSupportFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_and_support, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        title = view.findViewById(R.id.title_textview);
        title.setText(flag);
        send_button=view.findViewById(R.id.send_button);

        name_edittext=view.findViewById(R.id.name_edittext);
        message_edittext=view.findViewById(R.id.message_edittext);


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equalsIgnoreCase("Feedback")){
                    setFeedback(name_edittext.getText().toString(),message_edittext.getText().toString());
                }else if (flag.equalsIgnoreCase("Technical Support")){
                    setSupport(name_edittext.getText().toString(),message_edittext.getText().toString());
                }
            }
        });


    }

    private void setSupport(String subject,String message){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("subject",subject);
        params.put("message",message);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.support_URL, AppConstants.support_TAG, Banner.class, params);

    }

    private void setFeedback(String subject,String message){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("subject",subject);
        params.put("message",message);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.feedback_URL, AppConstants.feedback_TAG, General.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        General general=(General) result;

        Toast.makeText(getContext(),general.getData().toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}