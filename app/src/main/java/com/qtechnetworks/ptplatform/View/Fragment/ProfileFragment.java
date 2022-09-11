package com.qtechnetworks.ptplatform.View.Fragment;

import static com.qtechnetworks.ptplatform.Model.utilits.camera.Camera.captureImage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.Model.utilits.camera.Camera;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.ChoosingActivity;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.AddProgressDialog;
import com.qtechnetworks.ptplatform.View.Dialogs.EditProfileDialog;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileFragment extends Fragment {

    TextView username,joining_date;

    RoundedImageView profile_img;
    public static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 100;
    private static final String TAG = "TAG";
    public static final int REQUEST_GALLERY_CODE = 200, REQUEST_CAMERA = 400, WRITE_EXTERNAL_STORAGE_CODE=201;

    public static final int READ_REQUEST_CODE = 300;
    TextView assigned_coaches,progress,subscriptions,kyc,settings,logout;
    public static AlertDialog desDialog = null;
    ImageView editPin,editNamePin;
    View view;
    private EditProfileDialog editProfileDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        initial(view);

        return view;
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

        try{

            Glide.with(getContext()).load(
                    PreferencesUtils.getUser(getContext()).getAvatar()).
                    placeholder(R.drawable.logo).into(profile_img);

        }catch (Exception e){
            e.printStackTrace();
        }
        editNamePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileDialog = new EditProfileDialog(getContext());
                editProfileDialog.setCancelable(true);
                editProfileDialog.show();
                editProfileDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //TODO Up'date profile data.
                        joining_date.setText(PreferencesUtils.getUser(getContext()).getEmail());

                        username.setText(
                                PreferencesUtils.getUser(getContext()).getFirstName()
                                        +" "+PreferencesUtils.getUser(getContext()).getLastName());
                    }
                });
            }
        });
        editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hh", Toast.LENGTH_SHORT).show();
                if(ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
                {
                    getActivity().requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_REQUEST_CODE);


                }else{
                    if(ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA);
                    }else {
                        if (ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            getActivity().requestPermissions(
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    WRITE_EXTERNAL_STORAGE_CODE);
                        } else {

                                showGalleryFromFragment(getActivity());
                            }




                    }
                }

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

                PreferencesUtils.clearDefaults(getContext());
                startActivity(new Intent(getContext(), ChoosingActivity.class));
                getActivity().finish();

            }
        });

        joining_date.setText(PreferencesUtils.getUser(getContext()).getEmail());

        username.setText(
                PreferencesUtils.getUser(getContext()).getFirstName()
                        +" "+PreferencesUtils.getUser(getContext()).getLastName());

    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("OnActivityResult");
//        Log.d("OnActivityResult","added");
//
//            if (requestCode == GALLERY_REQUEST) {
//                // System.out.println("select file from gallery ");
//                Uri selectedImageUri = data.getData();
//                String tempPath = Camera.getPath(getActivity(),
//                        selectedImageUri);
//
//                Bitmap bm = null;
//                try {
//                    bm = Camera
//                            .decodeUri(getActivity(),selectedImageUri);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                profile_img.setImageBitmap(bm);
//            } else if (requestCode == CAMERA_REQUEST ) {
//                Bitmap photo = (Bitmap) data.getExtras()
//                        .get("data");
//                profile_img.setImageBitmap(photo);
//            }
//    }
    public void showGalleryFromFragment(final Activity activity) {




        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = inflater.inflate(R.layout.dialog_gallery, null);
        builder.setView(dialogView);

        dialogView.findViewById(R.id.Camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage(activity);
                desDialog.cancel();
            }
        });

        dialogView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(i, GALLERY_REQUEST);
                desDialog.cancel();
            }
        });

        desDialog = builder.create();
        desDialog.show();

    }
    private void setFragment(Fragment fragment ) {

        Bundle args = new Bundle();

        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

}