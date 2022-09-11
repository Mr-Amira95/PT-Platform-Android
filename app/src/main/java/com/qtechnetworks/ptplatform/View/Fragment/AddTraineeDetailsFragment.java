package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.Questions.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.Questions.Question;
import com.qtechnetworks.ptplatform.Model.Beans.calender.CalenderTime;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class AddTraineeDetailsFragment extends Fragment implements CallBack {

    TextView doneBtn;
    LinearLayout mRlayout;
    String coachId;
    LinearLayout.LayoutParams mRparams;
    List<EditText> questionsEt= new ArrayList<EditText>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trainee_details, container, false);
        if (PreferencesUtils.getCoach(getContext()) != null) {
            coachId=PreferencesUtils.getCoach(getContext()).getId().toString();
            getQuestions();
        }
        initials(view);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   setFragment(R.id.home_frame, new SuccessFragment("Checkout"));
                if(questionsEt.size()>0){
                    answerQuestions();
                }else {
                    setFragment(R.id.home_frame, new MainFragment());
                    Toast.makeText(getContext(), "No Questions to Answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void answerQuestions(){
        HashMap<String ,Object> params=new HashMap<>();
        List<HashMap<String, Object>> answers =new ArrayList<>();
        for (int i=0;i<questionsEt.size();i++) {
            if(!questionsEt.get(i).getText().toString().equals("")) {
                HashMap<String, Object> answer = new HashMap<>();
                answer.put("question_id", questionsEt.get(i).getId());
                answer.put("answer", questionsEt.get(i).getText().toString());
                answers.add(answer);

            }

        }
        JSONArray answersjs = new JSONArray(answers);
        params.put("coach_id", coachId);
        params.put("answers", answers);
        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().PostRaw(getContext(), AppConstants.COACH_QUESTIONS_ANSWER_URL, AppConstants.COACH_QUESTIONS_ANSWER_TAG, General.class, params);


    }
    private void getQuestions(){
        HashMap<String ,Object> params=new HashMap<>();
        params.put("coach_id",coachId);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.COACH_QUESTIONS_URL, AppConstants.COACH_QUESTIONS_TAG, Question.class, params);


    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initials(View view) {
        doneBtn = view.findViewById(R.id.done_txt);
         mRlayout = view.findViewById(R.id.questionsLayout);
          mRparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        switch (tag){
            case AppConstants.COACH_QUESTIONS_TAG :

                if(isSuccess) {
                    Question coachQuestiohns = (Question) result;
                    if (questionsEt != null) {
                        questionsEt.clear();
                    }
                    for (Datum queston : coachQuestiohns.getData()) {
                        TextView myTextView = new TextView(getContext());
                        myTextView.setLayoutParams(mRparams);
                        myTextView.setTextColor(Color.WHITE);
                        myTextView.setPadding(20,20,20,10);
                        myTextView.setLayoutParams(mRparams);
                        myTextView.setText(queston.getQuestion());

                        EditText myEditText = new EditText(getContext());
                        myEditText.setLayoutParams(mRparams);
                      //  myEditText.setHint(queston.getQuestion());
                        myEditText.setTextColor(Color.WHITE);
                        myEditText.setHintTextColor(Color.WHITE);
                        myEditText.setHeight(150);
                        myEditText.setMaxLines(1);
                        myEditText.setPadding(20,20,20,20);
                        myEditText.setBackgroundResource(R.drawable.background_radius_10);
                        //   myEditText.setGravity(Gravity.CENTER);
                        myEditText.setId(queston.getId());
                        questionsEt.add(myEditText);
                        mRlayout.addView(myTextView);
                        mRlayout.addView(myEditText);
                    }
                }else{
                    Toast.makeText(getContext(), "No questions to answer!", Toast.LENGTH_SHORT).show();
                }
            break;
            case AppConstants.COACH_QUESTIONS_ANSWER_TAG :
                if(isSuccess){
                    Toast.makeText(getContext(), "Answered Successfully!", Toast.LENGTH_SHORT).show();
                    setFragment(R.id.home_frame, new MainFragment());
                }else {
                    Toast.makeText(getContext(), "failed!", Toast.LENGTH_SHORT).show();
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