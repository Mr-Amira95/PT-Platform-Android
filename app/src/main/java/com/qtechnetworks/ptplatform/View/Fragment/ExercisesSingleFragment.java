package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.R;

public class ExercisesSingleFragment extends Fragment {

    RecyclerView videoRecyclerview;
    VideoItemAdapter videoAdapter;

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

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        videoAdapter = new VideoItemAdapter(getContext());
        videoRecyclerview.setAdapter(videoAdapter);
    }

}