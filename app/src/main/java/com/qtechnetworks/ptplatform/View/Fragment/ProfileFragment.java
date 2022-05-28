package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.ChoosingActivity;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

public class ProfileFragment extends Fragment {

    TextView username,joining_date;

    RoundedImageView profile_img;

    TextView assigned_coaches,progress,subscriptions,kyc,settings,logout;

    View view;

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

        joining_date.setText(
                PreferencesUtils.getUser(getContext()).getEmail());

        username.setText(
                PreferencesUtils.getUser(getContext()).getFirstName()
                        +" "+PreferencesUtils.getUser(getContext()).getLastName());

    }

    private void setFragment(Fragment fragment ) {

        Bundle args = new Bundle();

        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

}