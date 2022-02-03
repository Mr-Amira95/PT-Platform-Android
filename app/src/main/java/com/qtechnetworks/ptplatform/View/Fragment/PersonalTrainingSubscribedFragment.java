package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.CategoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.R;

public class PersonalTrainingSubscribedFragment extends Fragment {

    private ConstraintLayout noContentLayout;
    private LinearLayout contentLayout, homeCategoryLayout, PersonalizedCategoryLayout;
    private TextView homeCategoryTxt, personalizedCategoryTxt;
    boolean haveContent = true;

    private RecyclerView assignedWorkoutsRecyclerview, assignedMealsRecyclerview, videosRecyclerview, imagesRecyclerview, notesRecyclerview;
    private CoachAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_training_subscribed, container, false);

        initials(view);

        if (haveContent){
            noContentLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.INVISIBLE);
        }

        personalizedCategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeCategoryLayout.setVisibility(View.GONE);
                PersonalizedCategoryLayout.setVisibility(View.VISIBLE);
                personalizedCategoryTxt.setBackgroundResource(R.drawable.background_radius_20_title);
                homeCategoryTxt.setBackgroundResource(0);
            }
        });

        homeCategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeCategoryLayout.setVisibility(View.VISIBLE);
                PersonalizedCategoryLayout.setVisibility(View.GONE);
                homeCategoryTxt.setBackgroundResource(R.drawable.background_radius_20_title);
                personalizedCategoryTxt.setBackgroundResource(0);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        contentLayout = view.findViewById(R.id.content_layout);
        noContentLayout = view.findViewById(R.id.no_content_layout);

        homeCategoryLayout = view.findViewById(R.id.home_layout);
        PersonalizedCategoryLayout = view.findViewById(R.id.personalized_layout);

        homeCategoryTxt = view.findViewById(R.id.home_category);
        personalizedCategoryTxt = view.findViewById(R.id.personalised_category);

        assignedWorkoutsRecyclerview = view.findViewById(R.id.assigned_workouts_recyclerview);
        assignedMealsRecyclerview = view.findViewById(R.id.assigned_meals_recyclerview);
        videosRecyclerview = view.findViewById(R.id.video_recyclerView);
        imagesRecyclerview = view.findViewById(R.id.image_recyclerView);
        notesRecyclerview = view.findViewById(R.id.notes_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        assignedWorkoutsRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        assignedMealsRecyclerview.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        videosRecyclerview.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        imagesRecyclerview.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext());
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        notesRecyclerview.setLayoutManager(linearLayoutManager4);

        adapter = new CoachAdapter(getContext());
        assignedWorkoutsRecyclerview.setAdapter(adapter);
        assignedMealsRecyclerview.setAdapter(adapter);
        videosRecyclerview.setAdapter(adapter);
        imagesRecyclerview.setAdapter(adapter);
        notesRecyclerview.setAdapter(adapter);
    }
}