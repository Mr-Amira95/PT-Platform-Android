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

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemworkoutAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutVideo.VideoWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

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

    public TextView desc;

    public static SimpleExoPlayer player;

    String flag,ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag=getArguments().getString("flag");
            ID=getArguments().getString("ID");
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

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        if (flag.equalsIgnoreCase("Workout")){
            getWorkout(ID);
        }else {
            getExcerisis(ID);
        }


    }

    public void playinitial(String videourl) {


        player = new SimpleExoPlayer.Builder(requireContext()).build();

        video_view.setPlayer(player);
        video_view.setUseController(false);

        MediaItem mediaItem = MediaItem.fromUri(videourl);

        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        // Set the media item to be played.
        player.setMediaItem(mediaItem);

        // Prepare the player.
        player.prepare();

        // Start the playback.
        player.play();

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


        switch (tag){

            case AppConstants.exercise_videos_TAG:
                VideoExercises videoExercises=(VideoExercises) result;

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

                videoAdapter = new VideoItemAdapter(getContext(),videoExercises.getData(),ExercisesSingleFragment.this);
                videoRecyclerview.setAdapter(videoAdapter);

                break;

            case AppConstants.workout_videos_TAG:

                VideoWorkout videoWorkout=(VideoWorkout) result;

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

                VideoItemworkoutAdapter videoAdapter = new VideoItemworkoutAdapter(getContext(),videoWorkout.getData(),ExercisesSingleFragment.this);
                videoRecyclerview.setAdapter(videoAdapter);

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