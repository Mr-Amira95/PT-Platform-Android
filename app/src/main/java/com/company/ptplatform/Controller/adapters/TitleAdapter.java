package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.Exercises.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Fragment.ExercisesFragment;

import java.util.ArrayList;
import java.util.List;


public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> datum;
    private List<TextView> textViewList = new ArrayList<>();

    public TitleAdapter(Context context, List<Datum> datum) {
        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_title,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= datum.get(position);
        if (current.getTitle() != null)
            holder.title.setText(current.getTitle().toString());

        textViewList.add(holder.title);

        textViewList.get(0).setBackgroundResource(R.drawable.background_radius_20_title);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0; i<textViewList.size(); i++){
                    if (i == holder.getAdapterPosition()){
                        textViewList.get(i).setBackgroundResource(R.drawable.background_radius_20_title);
                        ExercisesFragment.setItems(current.getId().toString(), context);
                        ExercisesFragment.groupID = current.getId();
                    } else {
                        textViewList.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);

        }
    }

}
