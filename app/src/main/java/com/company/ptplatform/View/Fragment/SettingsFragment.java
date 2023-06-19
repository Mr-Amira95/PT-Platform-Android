package com.company.ptplatform.View.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.RegisterAndLogin.UpdateUserResults;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Dialogs.LanguagesDialog;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class SettingsFragment extends Fragment implements CallBack {

    TextView contactUs, language, termsConditions;
    Switch pushNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initials(view);
        clicks();

        return view;
    }

    private void clicks() {

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ContactFragment());
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguagesDialog languagesDialog = new LanguagesDialog(getContext());
                languagesDialog.show();
            }
        });

        pushNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setPushNotifications();
            }
        });

        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://ptplatform.app/public/app/terms-conditions"));
                startActivity(i);
            }
        });
    }

    private void initials(View view) {
        contactUs = view.findViewById(R.id.contact_us);
        language = view.findViewById(R.id.language);
        pushNotifications = view.findViewById(R.id.push_notificataions);
        termsConditions = view.findViewById(R.id.terms_conditions);

        pushNotifications.setChecked(PreferencesUtils.getUser(getContext()).getNotification());

        if (PreferencesUtils.getLanguage().equalsIgnoreCase("ar")){
            language.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_icon, 0, 0, 0);
            termsConditions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_icon, 0, 0, 0);
        }

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();
    }

    private void setPushNotifications(){
        HashMap<String ,Object> params = new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Notification_URL, AppConstants.Notification_TAG, UpdateUserResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        UpdateUserResults register = (UpdateUserResults) result;
        PreferencesUtils.setUser(register.getData(), getContext());
        pushNotifications.setChecked(PreferencesUtils.getUser(getContext()).getNotification());

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}