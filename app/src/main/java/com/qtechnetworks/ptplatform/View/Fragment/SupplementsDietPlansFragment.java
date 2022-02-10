package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.ChestAndBicepsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.SupplementsAndDietPlansAdapter;
import com.qtechnetworks.ptplatform.R;

public class SupplementsDietPlansFragment extends Fragment {

    EditText searchBar;
    TextView title;
    RecyclerView recyclerView;
    SupplementsAndDietPlansAdapter adapter;
    String flag;

    public SupplementsDietPlansFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplements_diet_plan, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        title = view.findViewById(R.id.title);
        title.setText(flag);

        searchBar = view.findViewById(R.id.search_bar);
        if(flag.equals("Supplements"))
            searchBar.setHint("SEARCH FOR SUPLEMENTS");
        else if (flag.equals("Recipes and Diet Plans"))
            searchBar.setHint("SEARCH FOR RECIPES");

        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


        adapter = new SupplementsAndDietPlansAdapter(getContext(), flag);
        recyclerView.setAdapter(adapter);
    }
}