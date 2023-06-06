package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.Coach.Datum;
import com.qtechnetworks.ptplatform.Model.utilits.PrefKeys;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.CoachesDialog;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;

import java.util.List;


public class CoachesAdapter extends RecyclerView.Adapter<CoachesAdapter.ViewHolder>  {

    private Context mContext;
    private List<Datum> data;
    CoachesDialog coachesDialog;

    public CoachesAdapter(Context mContext, List<Datum> data, CoachesDialog coachesDialog) {

        this.mContext=mContext;
        this.data=data;
        this.coachesDialog=coachesDialog;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current = data.get(position);

        holder.coachName.setText(current.getLastName());

        holder.coachName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesUtils.putString(PrefKeys.coachid,current.getId().toString());
                PreferencesUtils.setCoach(current, mContext);

                coachesDialog.dismiss();

                setFragment(new MainFragment());
            }
        });

    }

    private void setFragment(Fragment fragment) {

        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();
    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView coachName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coachName=itemView.findViewById(R.id.coach_name);
        }
    }

}
