package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.R;


public class NoteDialog extends Dialog {

    String note;
    Context context;
    Button yesBtn;
    TextView noteTxt;

    public NoteDialog(@NonNull Context context, String note) {
        super(context);
        this.note = note;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();

    }

    private void initials() {

        yesBtn = findViewById(R.id.yes_btn);
        noteTxt = findViewById(R.id.note);

        noteTxt.setText(note);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dismiss();
            }
        });
    }
}
