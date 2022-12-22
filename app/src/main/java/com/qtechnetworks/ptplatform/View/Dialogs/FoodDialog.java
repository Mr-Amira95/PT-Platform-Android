package com.qtechnetworks.ptplatform.View.Dialogs;

import static com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment.add_to_favourite;
import static com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment.add_to_workout;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.*;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachesAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.FoodsAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Food;
import com.qtechnetworks.ptplatform.Model.Beans.addto.Adtofavlog;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;

import java.util.HashMap;
import java.util.Locale;

import io.reactivex.disposables.Disposable;


public class FoodDialog extends Dialog implements CallBack {

    Context context;
    Button searchBtn;
    EditText searchBar;
    RecyclerView foodRecyclerview;

    public static Food food ;

    public FoodDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_food);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();
        getFood();

    }

    private void getFood() {

        HashMap <String ,Object> params=new HashMap<>();

        params.put("skip","0");
        params.put("name", searchBar.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.food_URL, AppConstants.food_TAG, Food.class, params);

    }

    private void initials() {

        searchBtn=findViewById(R.id.search_button);
        searchBar=findViewById(R.id.search_bar);
        foodRecyclerview=findViewById(R.id.food_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodRecyclerview.setLayoutManager(linearLayoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFood();
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard((MainActivity) context);
                    getFood();
                    return true;
                }
                return false;
            }
        });
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//        View view = activity.getCurrentFocus();
//
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {

            food=(Food) result;

            if (food.getData().size() >0) {
                double carb = food.getData().get(0).getCarb() * Integer.parseInt(weightnumber_edit.getText().toString());
                double fat = food.getData().get(0).getFat() * Integer.parseInt(weightnumber_edit.getText().toString());
                double protein=food.getData().get(0).getProtein() * Integer.parseInt(weightnumber_edit.getText().toString());
                int calories=food.getData().get(0).getCalorie() * Integer.parseInt(weightnumber_edit.getText().toString());
                fat_text.setText(fat+"");
                carb_text.setText(carb+"");
                protine_text.setText(protein+"");
                calories_text.setText(calories+"");
                Foodname_spinner.setText(food.getData().get(0).getName());
            } else {
                Toast.makeText(context, R.string.there_is_no_food_to_show, Toast.LENGTH_SHORT).show();
            }

            FoodsAdapter foodsAdapter = new FoodsAdapter (context, food.getData(), FoodDialog.this);
            foodRecyclerview.setAdapter(foodsAdapter);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
