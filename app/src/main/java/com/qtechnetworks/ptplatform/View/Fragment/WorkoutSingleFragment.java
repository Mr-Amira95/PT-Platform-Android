package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutVideo.VideoWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class WorkoutSingleFragment extends Fragment  {

    Button explore;
    TextView titleTxt, desc;
    ImageView img;

    String title, description, id, imgUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            title=getArguments().getString("title");
            description=getArguments().getString("description");
            imgUrl=getArguments().getString("img");
            id=getArguments().getString("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_single, container, false);

        initials(view);

        if (titleTxt != null)
            titleTxt.setText(title);

        if (description != null)
            desc.setText(description);

        Glide.with(getContext()).load(imgUrl).placeholder(R.drawable.logo).into(img);

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee"))
                    setFragmentWithoutBack ( new ExercisesSingleFragment(),"Workout",id);
                else
                    setFragmentWithoutBack( new ExercisesSingleFragment(),"Workout",id);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        img = view.findViewById(R.id.img);
        explore = view.findViewById(R.id.explore_btn);
        titleTxt = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

    }

    private void setFragment(Fragment fragment,String flag,String id) {

        Bundle args=new Bundle();
        args.putString("flag",flag);
        args.putString("ID",id);
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragmentWithoutBack(Fragment fragment,String flag,String id) {

        Bundle args=new Bundle();
        args.putString("flag",flag);
        args.putString("ID",id);
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

}