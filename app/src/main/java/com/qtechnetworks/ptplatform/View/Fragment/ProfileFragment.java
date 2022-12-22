package com.qtechnetworks.ptplatform.View.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.qtechnetworks.ptplatform.Model.utilits.camera.Camera.captureImage;
import static com.qtechnetworks.ptplatform.Model.utilits.camera.Camera.getPath;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.UpdateUserResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.Model.utilits.camera.Camera;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Activity.SplashActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.AddProgressDialog;
import com.qtechnetworks.ptplatform.View.Dialogs.EditProfileDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment implements CallBack {

    TextView username, joining_date, assigned_coaches,progress,subscriptions,kyc,settings,logout;
    RoundedImageView profile_img;
    ImageView editPin,editNamePin;
    private EditProfileDialog editProfileDialog;

    String filePath = "";
    File file;

    int tag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        EasyPermissions.requestPermissions(getActivity(), "Please accept permission", 233, Manifest.permission.READ_EXTERNAL_STORAGE);
        EasyPermissions.requestPermissions(getActivity(), "Please accept permission", 233, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        initial(view);
        clicks();

        return view;
    }

    private void clicks() {

        editNamePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileDialog = new EditProfileDialog(getContext());
                editProfileDialog.setCancelable(true);
                editProfileDialog.show();
            }
        });

        editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.showGalleryFromFragment(getActivity(), ProfileFragment.this);
            }
        });

        assigned_coaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new AssignedCoachesFragment());

            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new ProgressFragment());

            }
        });

        subscriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new ShopFragment());

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new SettingsFragment());

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutAPI();

            }
        });
    }

    private void logoutAPI() {

        tag = AppConstants.LOGOUT_TAG;

        HashMap<String ,Object> params = new HashMap<>();

        params.put("device_player_id", PreferencesUtils.getPlayerId());

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().Post(getContext(), AppConstants.LOGOUT_URL, AppConstants.LOGOUT_TAG, General.class, params);

    }

    private void editImg () {

        file = new File(filePath);

        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getPath(), surveyBody);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().PostFile(getContext(), AppConstants.UPDATE_AVATAR_URL, AppConstants.UPDATE_AVATAR_TAG, UpdateUserResults.class, body);

    }

    private void initial(View v){
        username=v.findViewById(R.id.username);
        joining_date=v.findViewById(R.id.joining_date);
        profile_img=v.findViewById(R.id.profile_img);
        editPin=v.findViewById(R.id.edit_profile_image_view);
        editNamePin=v.findViewById(R.id.edit_name_image_view);
        assigned_coaches=v.findViewById(R.id.assigned_coaches);
        progress=v.findViewById(R.id.progress);
        subscriptions=v.findViewById(R.id.subscriptions);
        kyc=v.findViewById(R.id.kyc);
        settings=v.findViewById(R.id.settings);
        logout=v.findViewById(R.id.logout);

        if (PreferencesUtils.getLanguage().equalsIgnoreCase("ar"))
            assigned_coaches.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_icon, 0, 0, 0);

        if (PreferencesUtils.getUser(getContext()).getAvatar() != null)
            Glide.with(getContext()).load(PreferencesUtils.getUser(getContext()).getAvatar()).placeholder(R.drawable.logo).into(profile_img);
        joining_date.setText(PreferencesUtils.getUser(getContext()).getEmail());
        if (PreferencesUtils.getUser(getContext()).getLastName() != null)
            username.setText(PreferencesUtils.getUser(getContext()).getFirstName() + " " + PreferencesUtils.getUser(getContext()).getLastName());
        else
            username.setText(PreferencesUtils.getUser(getContext()).getFirstName());

    }

    private void setFragment(Fragment fragment ) {

        Bundle args = new Bundle();
        fragment.setArguments(args);
        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragmentWithoutBack(Fragment fragment ) {

        Bundle args = new Bundle();
        fragment.setArguments(args);
        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){

            switch (tag){
                case AppConstants.LOGOUT_TAG:
                    General general = (General) result;

                    Toast.makeText(getContext(), general.getData().toString(), Toast.LENGTH_SHORT).show();
                    PreferencesUtils.clearDefaults(getContext());
                    startActivity(new Intent(getContext(), SplashActivity.class));
                    getActivity().finish();
                    break;

                case AppConstants.UPDATE_AVATAR_TAG:
                    UpdateUserResults updateUserResults = (UpdateUserResults) result;

                    Toast.makeText(getContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                    PreferencesUtils.setUser(updateUserResults.getData(),getContext());
                    setFragmentWithoutBack(new ProfileFragment());

                    break;
            }

        }
    }

    @Override
    public void onError(Throwable e) {

        if (tag == AppConstants.LOGOUT_TAG){
            PreferencesUtils.clearDefaults(getContext());
            startActivity(new Intent(getContext(), SplashActivity.class));
            getActivity().finish();
        }


    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("OnActivityResult", "" + requestCode + ",Result " + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }

        filePath = getPath(getActivity(), data.getData());
        file = new File(filePath);

        if(file.exists()){

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            try {
                Glide.with(getContext()).load(bitmap).into(profile_img);
            }catch (Exception e){
                e.printStackTrace();
            }

            editImg();

        }


        super.onActivityResult(requestCode, resultCode, data);
    }


}