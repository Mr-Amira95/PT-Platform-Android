package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachUserAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.TreineeAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Coach;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.Beans.PersonalCoach.PersonalCoach;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.CoachesDialog;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ChooseCoachFragment extends Fragment implements CallBack {

    RecyclerView coachRecyclerview;
    Button searchBtn;
    EditText searchBar;
    TextView title;

    String flag = "";

    public ChooseCoachFragment(String flag) {
        this.flag = flag;
    }

    public ChooseCoachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_coach, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {

        coachRecyclerview = view.findViewById(R.id.coach_recyclerview);
        title = view.findViewById(R.id.title_text);
        searchBtn = view.findViewById(R.id.search_button);
        searchBar = view.findViewById(R.id.search_bar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachRecyclerview.setLayoutManager(gridLayoutManager);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")) {
            getUsers();
            searchBar.setHint("Search Trainee");
            title.append("\nTrainee");
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
            searchBar.setHint("Search Coach");
            title.append("\nCoach");
            getCoach();
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")){
                    getUsersSearch();
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
                    getCoachSearch();
                }

            }
        });

    }

    private void getUsers() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Personal_Training_Coach_URL, AppConstants.Personal_Training_Coach_TAG, PersonalCoach.class, params);

    }

    private void getUsersSearch() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");
        params.put("name", searchBar.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Personal_Training_Coach_URL, AppConstants.Personal_Training_Coach_TAG, PersonalCoach.class, params);

    }

    private void getCoach() {

        HashMap <String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    private void getCoachSearch() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip", "0");
        params.put("name", searchBar.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){
            case AppConstants.users_coaches_TAG:
                Coach coach=(Coach) result;
                CoachUserAdapter coachAdapter = new CoachUserAdapter(getContext(),coach.getData());
                coachRecyclerview.setAdapter(coachAdapter);

                if (coach.getData().size() == 0)
                    Toast.makeText(getContext(), "There are no coaches to show", Toast.LENGTH_SHORT).show();

                break;

            case AppConstants.Personal_Training_Coach_TAG:
                PersonalCoach personalCoach = (PersonalCoach) result;
                TreineeAdapter treineeAdapter = new TreineeAdapter(getContext(), personalCoach.getData(), flag);
                coachRecyclerview.setAdapter(treineeAdapter);
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