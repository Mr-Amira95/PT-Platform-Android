package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.company.ptplatform.Controller.adapters.PersonalizedItemAdapter;
import com.company.ptplatform.R;

public class PersonalTrainingInnerFragment extends Fragment {

    WebView pdfView;
    ImageView image;

    int type;
    String url;

    PlayerView video_view;
    ExoPlayer player;

    public PersonalTrainingInnerFragment(int type, String url) {
        this.type = type;
        this.url = url;
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_training_inner, container, false);

        initials(view);

        switch (type){
            case PersonalizedItemAdapter.VIDEO_PERSONAL_TAG:
                pdfView.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                video_view.setVisibility(View.VISIBLE);
                playInitial(url);
                break;

            case PersonalizedItemAdapter.IMAGE_PERSONAL_TAG:
                pdfView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                video_view.setVisibility(View.GONE);
                viewImage(url);
                break;

            case PersonalizedItemAdapter.NOTE_PERSONAL_TAG:
                pdfView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                video_view.setVisibility(View.GONE);
                viewPDF(url);
                break;
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        pdfView = view.findViewById(R.id.pdfView);
        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.getSettings().setLoadsImagesAutomatically(true);
        pdfView.getSettings().setBuiltInZoomControls(true);

        image = view.findViewById(R.id.image);
        video_view=view.findViewById(R.id.video_view);
        player = new ExoPlayer.Builder(getContext()).build();
    }

    public void playInitial (String videourl) {

        video_view.setPlayer(player);
        video_view.setUseController(true);

        MediaItem mediaItem = MediaItem.fromUri(videourl);

        video_view.setControllerHideOnTouch(true);
        video_view.setUseController(true);
        video_view.showController();

        // Set the media item to be played.
        player.setMediaItem(mediaItem);

        // Prepare the player.
        player.prepare();

        // Start the playback.
        player.play();
    }

    public void viewImage (String imageUrl) {
        Glide.with(getContext()).load(imageUrl).placeholder(R.drawable.logo).into(image);
    }

    public void viewPDF (String pdfUrl) {
        pdfView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl);

//        pdfView.fromUri(Uri.parse("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl));
    }

}