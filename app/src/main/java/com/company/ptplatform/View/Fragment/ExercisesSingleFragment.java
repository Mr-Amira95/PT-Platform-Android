package com.company.ptplatform.View.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.company.ptplatform.Controller.adapters.VideoItemAdapter;
import com.company.ptplatform.Controller.adapters.VideoItemworkoutAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.General;
import com.company.ptplatform.Model.Beans.WorkoutVideo.VideoWorkout;
import com.company.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MediaViewActivity;
import com.company.ptplatform.View.Dialogs.AddLogDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ExercisesSingleFragment extends Fragment implements CallBack {

    RecyclerView videoRecyclerview;
    VideoItemAdapter videoAdapter;
    public PlayerView video_view;
    public static ExoPlayer player;
    ImageView expFullScreen;
    public static TextView desc, add_to_favourite, add_to_workout;
    TextView add_to_log;

    String flag, ID;
    public static int counter = 0;

    public static String VideoID;
    boolean secondPlay = false;

    String videolink, title, descrip, isFav, isToday;

    AddLogDialog addLogDialog;

    @Override
    public void onStop() {
        try {
            player.pause();
        } catch (Exception e){

        }
        super.onStop();
    }

    @Override
    public void onPause() {
        try {
            player.pause();
        } catch (Exception e){

        }
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

        counter = 0;

        if (getArguments() != null) {
            flag=getArguments().getString("flag");
            ID=getArguments().getString("ID");

            if (flag.equalsIgnoreCase("log")){

                VideoID = getArguments().getString("VideoID");

                videolink=getArguments().getString("video");

               title=getArguments().getString("title");
               descrip=getArguments().getString("description");

               isFav=getArguments().getString("is_fav");
               isToday=getArguments().getString("is_today");

            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercises_single, container, false);

        initial(view);
        clicks();

        //Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        add_to_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite(VideoID);
            }
        });


        add_to_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToWorkout(VideoID);

            }
        });

        add_to_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addLogDialog.show();

            }
        });

    }

    private void initial(View view) {

        player = new ExoPlayer.Builder(getContext()).build();

        videoRecyclerview= view.findViewById(R.id.video_recyclerView);

        expFullScreen=view.findViewById(R.id.fullscreen);
        video_view=view.findViewById(R.id.video_view);
        desc=view.findViewById(R.id.desc);

        add_to_favourite=view.findViewById(R.id.add_to_favourite);
        add_to_workout=view.findViewById(R.id.add_to_workout);
        add_to_log=view.findViewById(R.id.add_to_log);

        addLogDialog=new AddLogDialog(getContext(),ExercisesSingleFragment.this);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            add_to_favourite.setVisibility(View.GONE);
            add_to_workout.setVisibility(View.GONE);
            add_to_log.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            add_to_favourite.setVisibility(View.VISIBLE);
            add_to_workout.setVisibility(View.VISIBLE);
            add_to_log.setVisibility(View.VISIBLE);
        }

        if (flag.equalsIgnoreCase("Workout")) {
            if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
                getWorkoutCoach(ID);
            else
                getWorkout(ID);
        } else if (flag.equalsIgnoreCase("log")) {
            if (isFav.equalsIgnoreCase("true"))
                add_to_favourite.setText(R.string.remove_favourites);

            if (isToday.equalsIgnoreCase("true"))
                add_to_workout.setText(R.string.remove_workouts);

            playinitial(videolink);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                desc.setText(Html.fromHtml(descrip,Html.FROM_HTML_MODE_LEGACY));
            } else {
                desc.setText(Html.fromHtml(descrip));
            }

        } else if (flag.equalsIgnoreCase("personal_workout")) {
            getPersonalWorkout();
        } else {
            if (PreferencesUtils.getUserType().equalsIgnoreCase("coach"))
                getExcerisisCoach(ID);
            else
                getExcerisis(ID);

        }

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch(playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
                        break;
                    case ExoPlayer.STATE_ENDED:
                        if (counter<4){
                            player.seekTo(0);
                        }

                        counter++;

                        break;
                    case ExoPlayer.STATE_IDLE:
                        break;
                    case ExoPlayer.STATE_READY:
                        break;
                    default:
                        break;
                }
            }
        });

        player.setPlayWhenReady(true);//replay from start

    }

    public void playinitial (String videourl) {

        video_view.setPlayer(player);
        video_view.setUseController(true);

//        player.setRepeatMode(player.REPEAT_MODE_ONE);
        MediaItem mediaItem = MediaItem.fromUri(videourl);

        expFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MediaViewActivity.class).putExtra("url",videourl));

            }
        });
//        player.addMediaItems();
        //player.setRepeatMode(Player.REPEAT_MODE_ALL);

        video_view.setControllerHideOnTouch(true);
        video_view.setUseController(true);
        video_view.showController();

        // Set the media item to be played.
        player.setMediaItem(mediaItem);

        // Prepare the player.
        player.prepare();

        // Start the playback.
        player.play();
        secondPlay = true;

    }


    private void addToWorkout(String Videoid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("video_id",Videoid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, General.class, params);

    }

    private void addToFavorite(String Videoid){

        HashMap<String ,Object> params = new HashMap<>();

        params.put("video_id",Videoid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Favorite_URL, AppConstants.Add_Favorite_TAG, General.class, params);

    }


    private void getWorkout(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.workout_videos_URL, AppConstants.SHOW_WORKOUT_VIDS_TAG, VideoWorkout.class, params);

    }

    private void getPersonalWorkout(){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.SHOW_WORKOUT_VIDS+ID+"/show", AppConstants.SHOW_WORKOUT_VIDS_TAG, VideoWorkout.class, params);
    }

    private void getWorkoutCoach(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.workout_videos_URL, AppConstants.SHOW_WORKOUT_VIDS_TAG, VideoWorkout.class, params);

    }


    private void getExcerisis(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("coach_id",PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.exercise_videos_URL, AppConstants.exercise_videos_TAG, VideoExercises.class, params);
    }

    private void getExcerisisCoach(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.exercise_videos_URL, AppConstants.exercise_videos_TAG, VideoExercises.class, params);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        General general;

        switch (tag){

            case AppConstants.Add_Favorite_TAG:

                general=(General) result;
                Toast.makeText(getContext(),general.getData().toString(),Toast.LENGTH_LONG).show();
                break;

            case AppConstants.Add_Workout_TAG:

                general=(General) result;
                Toast.makeText(getContext(),general.getData().toString(),Toast.LENGTH_LONG).show();
                break;

            case AppConstants.exercise_videos_TAG:

                VideoExercises videoExercises=(VideoExercises) result;

                if (videoExercises.getData().size()>0){
                    if (videoExercises.getData().get(0).getIsFavourite()){
                        add_to_favourite.setText(R.string.remove_favourites);
                    }

                    if (videoExercises.getData().get(0).getIsWorkout()){
                        add_to_workout.setText(R.string.remove_workouts);
                    }

                    try {
                        video_view.setDefaultArtwork(drawableFromUrl(videoExercises.getData().get(0).getImage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        if (videoExercises.getData().get(0).getDescription() != null)
                            desc.setText(Html.fromHtml(videoExercises.getData().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        if (videoExercises.getData().get(0).getDescription() != null)
                            desc.setText(Html.fromHtml(videoExercises.getData().get(0).getDescription()));
                    }

                    playinitial(videoExercises.getData().get(0).getVideo());

                    videoAdapter = new VideoItemAdapter(getContext(),videoExercises.getData(),ExercisesSingleFragment.this,add_to_favourite,add_to_workout,add_to_log);
                    videoRecyclerview.setAdapter(videoAdapter);

                    VideoID = videoExercises.getData().get(0).getId().toString();

                } else {
                    Toast.makeText(getContext(), R.string.no_videos, Toast.LENGTH_SHORT).show();
                }

                break;

            case AppConstants.SHOW_WORKOUT_VIDS_TAG:

                VideoWorkout videoWorkout = (VideoWorkout) result;
                if(!videoWorkout.getData().isEmpty()) {
                    if (videoWorkout.getData().get(0).getIsFavourite()) {
                        add_to_favourite.setText(R.string.remove_favourites);
                    }

                    if (videoWorkout.getData().get(0).getIsWorkout()) {
                        add_to_workout.setText(R.string.remove_workouts);
                    }

                    if (videoWorkout.getData().size() > 0) {
                        try {
                            video_view.setDefaultArtwork(drawableFromUrl(videoWorkout.getData().get(0).getImage()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (videoWorkout.getData().get(0).getDescription() != null)
                            desc.setText(Html.fromHtml(videoWorkout.getData().get(0).getDescription()));

                        playinitial(videoWorkout.getData().get(0).getVideo());

                        VideoItemworkoutAdapter videoAdapter = new VideoItemworkoutAdapter(getContext(), videoWorkout.getData(), ExercisesSingleFragment.this);
                        videoRecyclerview.setAdapter(videoAdapter);

                        VideoID = videoWorkout.getData().get(0).getId().toString();
                    }
                }
                break;


        }


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;
        InputStream input = null;
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            input = connection.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

}