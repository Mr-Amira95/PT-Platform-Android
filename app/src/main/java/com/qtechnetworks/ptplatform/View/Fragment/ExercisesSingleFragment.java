package com.qtechnetworks.ptplatform.View.Fragment;

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

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemworkoutAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutVideo.VideoWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.addto.Adtofavlog;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.AddLogDialog;

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

    public TextView desc,add_to_favourite,add_to_workout,add_to_log;

    public static SimpleExoPlayer player;

    String flag,ID;

    public String VideoID;
    boolean secondPlay = false;

    String videolink,title,descrip;

    AddLogDialog addLogDialog;

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
            flag=getArguments().getString("flag");
            ID=getArguments().getString("ID");

            if (flag.equalsIgnoreCase("log")){

                VideoID=getArguments().getString("VideoID");

                videolink=getArguments().getString("video");

               title=getArguments().getString("title");
               descrip=getArguments().getString("description");

            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercises_single, container, false);

        initial(view);

        //Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        videoRecyclerview= view.findViewById(R.id.video_recyclerView);

        video_view=view.findViewById(R.id.video_view);

        video_view=view.findViewById(R.id.video_view);
        desc=view.findViewById(R.id.desc);

        add_to_favourite=view.findViewById(R.id.add_to_favourite);
        add_to_workout=view.findViewById(R.id.add_to_workout);
        add_to_log=view.findViewById(R.id.add_to_log);

        addLogDialog=new AddLogDialog(getContext(),ExercisesSingleFragment.this);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        if (flag.equalsIgnoreCase("Workout")){
            getWorkout(ID);
        }else if(flag.equalsIgnoreCase("log")){

            playinitial(videolink);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                desc.setText(Html.fromHtml(descrip,Html.FROM_HTML_MODE_LEGACY));
            } else {
                desc.setText(Html.fromHtml(descrip));
            }



        } else {
            getExcerisis(ID);
        }

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

    public void playinitial (String videourl) {

        player = new SimpleExoPlayer.Builder(getContext()).build();

        video_view.setPlayer(player);
        video_view.setUseController(false);

        MediaItem mediaItem = MediaItem.fromUri(videourl);

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

    public void addToLog(String Videoid,String number,String weight,String weightunit,String repetition,String note){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("video_id",Videoid);
        params.put("number",number);
        params.put("weight",weight);
        params.put("weight_unit",weightunit);
        params.put("repetition",repetition);
        params.put("note",note);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, Adtofavlog.class, params);

    }

    private void addToWorkout(String Videoid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("video_id",Videoid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, General.class, params);

    }


    private void addToFavorite(String Videoid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("video_id",Videoid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.Add_Favorite_URL, AppConstants.Add_Favorite_TAG, General.class, params);

    }


    private void getWorkout(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.workout_videos_URL, AppConstants.workout_videos_TAG, VideoWorkout.class, params);

    }


    private void getExcerisis(String Exid){

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

            case AppConstants.Add_Log_TAG:

                Adtofavlog adtofavlog=(Adtofavlog) result;

                if (adtofavlog.getData().getIsFavourite()){
                    add_to_favourite.setText("Remove from favourite");
                }
                if (adtofavlog.getData().getIsTodayLog()){
                    add_to_log.setText("Remove from log");
                }
                if (adtofavlog.getData().getIsWorkout()){
                    add_to_workout.setText("Remove from Workout");
                }

                break;

            case AppConstants.Add_Workout_TAG:

                general=(General) result;

                Toast.makeText(getContext(),general.getData().toString(),Toast.LENGTH_LONG).show();

                break;

            case AppConstants.exercise_videos_TAG:

                VideoExercises videoExercises=(VideoExercises) result;

                if (videoExercises.getData().get(0).getIsFavourite()){
                    add_to_favourite.setText("Remove from favourite");
                }
                if (videoExercises.getData().get(0).getIsTodayLog()){
                    add_to_log.setText("Remove from log");
                }
                if (videoExercises.getData().get(0).getIsWorkout()){
                    add_to_workout.setText("Remove from Workout");
                }

                try {
                    video_view.setDefaultArtwork(drawableFromUrl(videoExercises.getData().get(0).getImage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    desc.setText(Html.fromHtml(videoExercises.getData().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    desc.setText(Html.fromHtml(videoExercises.getData().get(0).getDescription()));
                }

                playinitial(videoExercises.getData().get(0).getVideo());

                videoAdapter = new VideoItemAdapter(getContext(),videoExercises.getData(),ExercisesSingleFragment.this,add_to_favourite,add_to_workout,add_to_log);
                videoRecyclerview.setAdapter(videoAdapter);

                VideoID=videoExercises.getData().get(0).getId().toString();


                break;

            case AppConstants.workout_videos_TAG:

                VideoWorkout videoWorkout=(VideoWorkout) result;

                if (videoWorkout.getData().get(0).getIsFavourite()){
                    add_to_favourite.setText("Remove from favourite");
                }
                if (videoWorkout.getData().get(0).getIsTodayLog()){
                    add_to_log.setText("Remove from log");
                }
                if (videoWorkout.getData().get(0).getIsWorkout()){
                    add_to_workout.setText("Remove from Workout");
                }

                try {
                    video_view.setDefaultArtwork(drawableFromUrl(videoWorkout.getData().get(0).getImage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    desc.setText(Html.fromHtml(videoWorkout.getData().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    desc.setText(Html.fromHtml(videoWorkout.getData().get(0).getDescription()));
                }

                playinitial(videoWorkout.getData().get(0).getVideo());

                VideoItemworkoutAdapter videoAdapter = new VideoItemworkoutAdapter(getContext(),videoWorkout.getData(),ExercisesSingleFragment.this,add_to_favourite,add_to_workout,add_to_log);
                videoRecyclerview.setAdapter(videoAdapter);

                VideoID=videoWorkout.getData().get(0).getId().toString();

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