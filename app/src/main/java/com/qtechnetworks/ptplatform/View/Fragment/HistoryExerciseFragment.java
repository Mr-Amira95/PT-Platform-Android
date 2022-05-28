package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutHistoryAdapter;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.AddLogDialog;

import java.util.ArrayList;

public class HistoryExerciseFragment extends Fragment {

    TextView sun, mon, tue, wed, thu, fri, sat;
    ArrayList<TextView> daysList = new ArrayList<>();

    RecyclerView videoRecyclerview;
    VideoItemAdapter videoAdapter;
    public PlayerView video_view;
    public TextView desc;

    public static SimpleExoPlayer player;

    public String VideoID;
    boolean secondPlay = false;

    public HistoryExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        player.pause();
        super.onStop();
    }

    @Override
    public void onPause() {
        player.pause();
        super.onPause();
    }

    @Override
    public void onStart() {
        if (secondPlay){
            player.play();
        }
        super.onStart();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_history, container, false);

        initial(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    public void playinitial (String videourl) {

        player = new SimpleExoPlayer.Builder(getContext()).build();

        video_view.setPlayer(player);
        video_view.setUseController(false);

        MediaItem mediaItem = MediaItem.fromUri(videourl);

        //player.setRepeatMode(Player.REPEAT_MODE_ALL);

        video_view.setControllerHideOnTouch(true);
        video_view.showController();

        // Set the media item to be played.
        player.setMediaItem(mediaItem);

        // Prepare the player.
        player.prepare();

        // Start the playback.
        player.play();
        secondPlay = true;

    }

    private void clicks() {
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(0);
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(1);
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(2);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(3);
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(4);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(5);
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(6);
            }
        });

    }

    private void setBackground(int index) {
        for (int i=0; i<daysList.size(); i++){
            if ( i == index){
                daysList.get(i).setBackgroundResource(R.drawable.button_background);
            } else {
                daysList.get(i).setBackgroundResource(R.color.tran);
            }
        }
    }

    private void initial(View view) {

        videoRecyclerview= view.findViewById(R.id.video_recyclerView);

        video_view=view.findViewById(R.id.video_view);

        video_view=view.findViewById(R.id.video_view);
        desc=view.findViewById(R.id.desc);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        sun = view.findViewById(R.id.sun);
        mon = view.findViewById(R.id.mon);
        tue = view.findViewById(R.id.tue);
        wed = view.findViewById(R.id.wed);
        thu = view.findViewById(R.id.thu);
        fri = view.findViewById(R.id.fri);
        sat = view.findViewById(R.id.sat);

        daysList.add(sun);
        daysList.add(mon);
        daysList.add(tue);
        daysList.add(wed);
        daysList.add(thu);
        daysList.add(fri);
        daysList.add(sat);
    }

}