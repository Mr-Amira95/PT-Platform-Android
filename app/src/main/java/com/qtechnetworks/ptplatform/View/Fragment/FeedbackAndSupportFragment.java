package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.onesignal.OneSignal;
import com.qtechnetworks.ptplatform.Controller.NewNetworking.RetrofitClient;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;

public class FeedbackAndSupportFragment extends Fragment {

    TextView title;
    String flag="";

    Button send_button;
    EditText name_edittext, message_edittext;

    public FeedbackAndSupportFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_and_support, container, false);

        initial(view);
        clicks();

        return view;
    }

    private void clicks() {

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name_edittext.getText().toString().isEmpty() && message_edittext.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
                } else if (name_edittext.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), R.string.please_fill_title_field, Toast.LENGTH_SHORT).show();
                } else if (message_edittext.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), R.string.please_fill_message_field, Toast.LENGTH_SHORT).show();
                } else {
                    if (flag.equalsIgnoreCase("Feedback")) {
                        feedback(name_edittext.getText().toString(), message_edittext.getText().toString());
                    } else if (flag.equalsIgnoreCase("Technical Support")){
                        support(name_edittext.getText().toString(), message_edittext.getText().toString());
                    }
                }

            }
        });

    }

    private void initial(View view) {
        title = view.findViewById(R.id.title_textview);
        if (flag.equalsIgnoreCase("Feedback")){
            title.setText(R.string.feedback);
        } else {
            title.setText(R.string.technical_support);
        }

        send_button=view.findViewById(R.id.send_button);

        name_edittext=view.findViewById(R.id.name_edittext);
        message_edittext=view.findViewById(R.id.message_edittext);
    }

    private void support(String subject,String message) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("subject", subject);
        params.put("message", message);

        Call<General> call = RetrofitClient.getInstance().getMyApi().supportForm(AppConstants.support_URL, params);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, retrofit2.Response<General> response) {

                if (response.isSuccessful()){
                    General general = response.body();

                    if (general.getSuccess()){
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getContext(), MainActivity.class);
                        i.putExtra("flag","support");
                        startActivity(i);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.the_message_must_be_at_least_20_characters, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {
                Toast.makeText(getContext(), R.string.an_error_has_occurred, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void feedback(String subject,String message) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("subject", subject);
        params.put("message", message);

        Call<General> call = RetrofitClient.getInstance().getMyApi().supportForm(AppConstants.feedback_URL, params);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, retrofit2.Response<General> response) {

                if (response.isSuccessful()){
                    General general = response.body();

                    if (general.getSuccess()){
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getContext(), MainActivity.class);
                        i.putExtra("flag","support");
                        startActivity(i);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), String.valueOf(general.getData()), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.the_message_must_be_at_least_20_characters, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {
                Toast.makeText(getContext(), R.string.an_error_has_occurred, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}