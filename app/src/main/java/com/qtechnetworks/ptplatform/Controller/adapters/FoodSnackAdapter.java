package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Dinner;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Snack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.FoodFragment;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class FoodSnackAdapter extends RecyclerView.Adapter<FoodSnackAdapter.ViewHolder> implements CallBack {

    private Context context;
    List<Snack> data;

    public FoodSnackAdapter(Context context, List<Snack> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Snack current= data.get(position);

        holder.food_title.setText(current.getTitle().toString());
        holder.food_details.setText(current.getName().toString());
        holder.food_value.setText(UtilisMethods.doubleFormat(current.getCalorie()));

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFood(current.getUserFoodID());
            }
        });
    }

    private void setFragment(Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void deleteFood(int id) {

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().delete(context, AppConstants.fooduser_URL+"/"+id, AppConstants.fooduser_TAG, General.class);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        General general = (General) result;
        setFragment(new FoodFragment(), (MainActivity)context);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView food_title,food_details,food_value;
        ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            food_title=itemView.findViewById(R.id.food_title);
            food_details=itemView.findViewById(R.id.food_details);
            food_value=itemView.findViewById(R.id.food_value);
            deleteIcon=itemView.findViewById(R.id.delete_icon);

        }
    }

}
