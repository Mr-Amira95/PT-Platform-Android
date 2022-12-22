package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Category;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.ExercisesResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.EndlessRecyclerViewScrollListener;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesFragment;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> implements CallBack {

    private Context context;
    private List<Category> data;
    private int selectedCategory;

    ChestAndBicepsAdapter adapter;

    public ExercisesAdapter(Context context, List<Category> data) {
        this.context = context;
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
        selectedCategory = current.getId();

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.exercise_recyclerview.setLayoutManager(linearLayoutManager3);

        adapter = new ChestAndBicepsAdapter (context, current.getExercises());
        holder.exercise_recyclerview.setAdapter(adapter);

        if (current.getTitle() != null)
            holder.title_text.setText(current.getTitle().toString());

        holder.exercise_recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager3) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
                    getCategoryExercisesCoach(String.valueOf(current.getId()),totalItemsCount);
                } else {
                    getCategoryExercises(String.valueOf(current.getId()),totalItemsCount);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void getCategoryExercises(String categoryID, int skip) {

        HashMap<String ,Object> params = new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(context).getId());
        params.put("category_id", categoryID);
        params.put("skip", skip);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(context, AppConstants.exercise_URL, AppConstants.exercise_TAG, ExercisesResults.class, params);
    }

    private void getCategoryExercisesCoach(String categoryID, int skip) {

        HashMap<String ,Object> params = new HashMap<>();

        params.put("category_id", categoryID);
        params.put("skip", skip);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(context, AppConstants.exercise_URL, AppConstants.exercise_TAG, ExercisesResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        ExercisesResults exercisesResults = (ExercisesResults) result;

        if(!exercisesResults.getData().isEmpty()) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).getId() == selectedCategory) {
                    for (int i = 0; i < exercisesResults.getData().size(); i++) {
                        data.get(0).getExercises().add(exercisesResults.getData().get(i));
                    }
                    j = data.size();
                }
            }

            adapter.notifyDataSetChanged();
        }
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

//    @Override
//    public int getItemViewType(int position) {
//        if (position == ExercisesFragment.counter)
//
//        Toast.makeText(context, position + " Items", Toast.LENGTH_SHORT).show();
//        return position;
//    }


}
