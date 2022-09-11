package com.qtechnetworks.ptplatform.View.Activity;

import static com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment.CAMERA_REQUEST;
import static com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment.GALLERY_REQUEST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.User;
import com.qtechnetworks.ptplatform.Model.Beans.calender.CalenderTime;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.Model.utilits.camera.Camera;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.CalendarFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesWorkoutFragment;
import com.qtechnetworks.ptplatform.View.Fragment.HomeFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NutritionFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ShopFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements CallBack {

    private FrameLayout frameLayout;
    private TextView home, coach, profile;
    public static Activity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initials();
        clicks();
        me = this;
        if (PreferencesUtils.getCoach(MainActivity.this) != null) {
            //    setFragmentwithoutBack(new MainFragment("shop"));
            if (getIntent().getExtras() != null && getIntent().getExtras().getString("page").equals("shop")) {

                setFragmentwithoutBack(new MainFragment("shop"));

            } else {
                setFragmentwithoutBack(new MainFragment());
            }
        } else {
            setFragmentwithoutBack(new HomeFragment());
        }

    }

    private void clicks() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new HomeFragment());
            }
        });

        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferencesUtils.getCoach(MainActivity.this) != null) {
                    setFragment(new MainFragment());
                } else {
                    setFragment(new HomeFragment());
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ProfileFragment());
            }
        });
    }

    private void initials() {
        frameLayout = findViewById(R.id.home_frame);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        coach = findViewById(R.id.coach);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFragmentwithoutBack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("OnActivityResult");
        Log.d("OnActivityResult","---------------------------added-"+resultCode);

        if (requestCode == GALLERY_REQUEST) {
            // System.out.println("select file from gallery ");
            Uri selectedImageUri = data.getData();
            String tempPath = Camera.getPath(this,
                    selectedImageUri);

            Bitmap bm = null;
            try {
                bm = Camera
                        .decodeUri(this,selectedImageUri);
                Log.d("avatar","-GALLERY_REQUEST--------------------------added");
                File file=new File(tempPath);
                setAvatar(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
          //  profile_img.setImageBitmap(bm);

        } else if (requestCode == CAMERA_REQUEST ) {
            Bitmap photo = (Bitmap) data.getExtras()
                    .get("data");

            setAvatar(bitmapToFile(getApplicationContext(),photo,"avatar"));
          //  profile_img.setImageBitmap(photo);
        }
    }
    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }
    private void setAvatar(File file){


        HashMap<String ,RequestBody> params=new HashMap<>();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart[] = {MultipartBody.Part.createFormData(
                "avatar",
                "avatar.jpg",
                requestBody
        )};
        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().PostFile(getApplicationContext(),
                AppConstants.UPDATE_AVATAR_URL, AppConstants.UPDATE_AVATAR_TAG, User.class, params,filePart);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}