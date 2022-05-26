package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Category;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.CategoryResults;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.ExercisesResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.EndlessRecyclerViewScrollListener;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class WorkoutAndExsircisesAdapter extends RecyclerView.Adapter<WorkoutAndExsircisesAdapter.ViewHolder> implements CallBack {

    private Context context;
    private String flag;
    List<Category> data;
    int selectedCategory;
    RecyclerView recyclerView;

    ChestAndBicepsAdapter adapter;

    public WorkoutAndExsircisesAdapter(Context context, String flag,List<Category> data) {

        this.context = context;
        this.flag = flag;
        this.data = data;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exircises,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category current= data.get(position);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.exercise_recyclerview.setLayoutManager(linearLayoutManager3);

        adapter = new ChestAndBicepsAdapter (context,flag,current.getExercises());
        holder.exercise_recyclerview.setAdapter(adapter);

        holder.title_text.setText(current.getTitle().toString());

        holder.exercise_recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager3) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                selectedCategory = current.getId();
                if (flag.equals("Workout")){

                    getCategoryWorkouts(String.valueOf(current.getId()),totalItemsCount);

                }else if (flag.equals("Exercises")){

                    getCategoryExercises(String.valueOf(current.getId()),totalItemsCount);

                }

            }
        });

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void getCategoryExercises(String categoryID, int skip) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("category_id", categoryID);
        params.put("skip", skip);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(context, AppConstants.exercise_URL, AppConstants.exercise_TAG, ExercisesResults.class, params);
    }


    private void getCategoryWorkouts(String categoryID, int skip){

        HashMap<String ,Object> params=new HashMap<>();
        params.put("category_id",categoryID);
        params.put("skip",skip);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(context, AppConstants.workout_URL, AppConstants.workout_TAG, ExercisesResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        ExercisesResults exercisesResults = (ExercisesResults) result;

        for (int j=0; j<data.size(); j++){
            if (data.get(j).getId().equals(selectedCategory)){
                for (int i=0; i<exercisesResults.getData().size(); i++){
                    data.get(0).getExercises().add(exercisesResults.getData().get(i));
                }
                j = data.size();
            }
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_text;
        public RecyclerView exercise_recyclerview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_text=itemView.findViewById(R.id.title_text);
            exercise_recyclerview=itemView.findViewById(R.id.exercise_recyclerview);
        }
    }

}
