package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.workout.CategoryResults;
import com.qtechnetworks.ptplatform.Model.Beans.workout.Datum;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.WorkoutFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class WorkoutTitleAdapter extends RecyclerView.Adapter<WorkoutTitleAdapter.ViewHolder>  implements CallBack {

    private Context context;
    private List<Datum> datum;
    private List<TextView> textViewList = new ArrayList<>();

    public WorkoutTitleAdapter(Context context, List<Datum> datum) {
        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_title,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= datum.get(position);

        holder.title.setText(current.getTitle().toString());

        textViewList.add(holder.title);

        textViewList.get(0).setBackgroundResource(R.drawable.background_radius_20_title);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<textViewList.size(); i++){
                    if (i == holder.getAdapterPosition()){
                        textViewList.get(i).setBackgroundResource(R.drawable.background_radius_20_title);
                        getCategory(current.getId());

                        //setFragment(new WorkoutFragment(), PreferencesUtils.getCoach(context).getId().toString());
                    } else {
                        textViewList.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });
    }

    private void getCategory(Integer id) {
        HashMap<String ,Object> params = new HashMap<>();
        params.put("group_id",id);
        params.put("skip",0);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(context, AppConstants.category_Workout_URL, AppConstants.category_Workout_TAG, CategoryResults.class, params);
    }

    private void setFragment(Fragment fragment,String coachid) {

        Bundle args = new Bundle();
        args.putString("coachid",coachid);
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            CategoryResults categoryResults = (CategoryResults) result;
            WorkoutFragment.setItems(context, categoryResults.getData());
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);

        }
    }

}
