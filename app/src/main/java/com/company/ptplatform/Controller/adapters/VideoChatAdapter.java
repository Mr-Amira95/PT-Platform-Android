package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.VideoChat.Datum;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Dialogs.ConfirmationDialog;

import java.util.List;

public class VideoChatAdapter extends RecyclerView.Adapter<VideoChatAdapter.ViewHolder> {

    private Context context;
    private List<Datum> datum;

    ConfirmationDialog confirmationDialog;

    public VideoChatAdapter(Context context, List<Datum> datum) {
        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public VideoChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_chat_item,parent,false);

        return new VideoChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoChatAdapter.ViewHolder holder, int position) {

        Datum current= datum.get(position);

        holder.dateTime.setText(current.getDate()+" / "+current.getTime());
        holder.sessionName.setText(PreferencesUtils.getCoach(context).getLastName());

        holder.joinSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current.getCoachTimeReservation() != null){
                    if (current.getCoachTimeReservation().getStatus().equalsIgnoreCase("accept")){
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(current.getCoachTimeReservation().getZoom().getData().getStartUrl()));
                        try{
                            context.startActivity(intent);
                        } catch(Exception e) {
                            Toast.makeText(view.getContext(), "Can't start Video chat", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(view.getContext(), "wait until coach approval", Toast.LENGTH_SHORT).show();
                    }
                } else if (current.getCoachReservation() != null){
                    if (current.getCoachReservation().getStatus().equalsIgnoreCase("accept")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(current.getCoachReservation().getZoom().getData().getStartUrl()));
                        try{
                            context.startActivity(intent);}
                        catch(Exception e) {
                            Toast.makeText(view.getContext(), "Can't start Video chat", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(view.getContext(), "wait until coach approval", Toast.LENGTH_SHORT).show();
                    }
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(current.getCoachReservation().getZoom().getData().getStartUrl()));
                } else
                    Toast.makeText(view.getContext(), "Wait for coach accept", Toast.LENGTH_SHORT).show();

            }
        });

        holder.cancelSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog = new ConfirmationDialog(context, String.valueOf(current.getId()));
                confirmationDialog.setCancelable(true);
                confirmationDialog.show();
            }
        });
        //textViewList.add(holder.title);

    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTime, sessionName;
        public Button joinSessionBtn,cancelSessionBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sessionName=itemView.findViewById(R.id.session_name);
            joinSessionBtn=itemView.findViewById(R.id.join_session_btn);
            dateTime=itemView.findViewById(R.id.session_date_time);
            cancelSessionBtn=itemView.findViewById(R.id.cancel_session_btn);

        }
    }

}