package com.qtechnetworks.ptplatform.Controller.adapters;

import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.Foodname_spinner;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.calories_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.carb_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.fat_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.protine_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.weightnumber_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Log.LogResults;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutHistory.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutHistory.WorkoutHistoryResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.FoodDialog;
import com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment;
import com.qtechnetworks.ptplatform.View.Fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class ExerciseHistoryAdapter extends RecyclerView.Adapter<ExerciseHistoryAdapter.ViewHolder> implements CallBack {

    private Context mContext;
    private WorkoutHistoryResults workoutHistoryResults;
    private ArrayList <RecyclerView> recyclerViews = new ArrayList<>();
    private RecyclerView selectedItem;

    public ExerciseHistoryAdapter(Context mContext, WorkoutHistoryResults workoutHistoryResults) {
        this.workoutHistoryResults = workoutHistoryResults;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exercise_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current = workoutHistoryResults.getData().get(position);

        holder.exerciseName.setText(current.getTitle());
        Glide.with(mContext).load(current.getImage()).placeholder(R.drawable.logo).into(holder.exerciseImg);

        recyclerViews.add(holder.recyclerView);

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (recyclerViews.get(position).getVisibility() == View.VISIBLE){
                    recyclerViews.get(position).setVisibility(View.GONE);
                } else {
                    for (int i =0; i<recyclerViews.size(); i++){
                        recyclerViews.get(i).setVisibility(View.GONE);
                    }

                    selectedItem = recyclerViews.get(position);
                    selectedItem.setVisibility(View.VISIBLE);

                    if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
                        getLogTrainee(String.valueOf(current.getId()));
                    else if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
                        getLogCoach(String.valueOf(current.getId()));

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistoryResults.getData().size();
    }

    private void getLogCoach(String exerciseID) {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", HistoryFragment.userID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(mContext, AppConstants.Add_Log_URL+"/"+exerciseID+"/show", AppConstants.Add_Log_TAG, LogResults.class, params);
    }

    private void getLogTrainee(String exerciseID) {

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(mContext, AppConstants.Add_Log_URL+"/"+exerciseID+"/show", AppConstants.Add_Log_TAG, LogResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){

            LogResults logResults = (LogResults) result;

            ExerciseHRecordAdapter exerciseHRecordAdapter = new ExerciseHRecordAdapter(mContext, logResults);
            selectedItem.setAdapter(exerciseHRecordAdapter);

            if (logResults.getData().size()==0)
                Toast.makeText(mContext, "There are no related logs to the selected exercise", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView exerciseImg;
        public TextView exerciseName;
        public LinearLayout exerciseLayout;
        public RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseImg=itemView.findViewById(R.id.exercise_icon);
            exerciseName=itemView.findViewById(R.id.exercise_name);
            recyclerView = itemView.findViewById(R.id.records_recyclerview);
            exerciseLayout=itemView.findViewById(R.id.exercise_layout);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);


        }
    }

}
