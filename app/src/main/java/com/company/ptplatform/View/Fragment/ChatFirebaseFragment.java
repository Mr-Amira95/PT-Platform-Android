package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.company.ptplatform.Controller.adapters.MessagesAdapter;
import com.company.ptplatform.Model.Beans.Chat.Message;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;

import java.util.ArrayList;
import java.util.Date;

public class ChatFirebaseFragment extends Fragment {

    private RecyclerView chatRecyclerView;
    private ImageView sendIcon;
    private EditText messageEditText;

    String id;

    FirebaseDatabase database;
    ArrayList<Message> messageArrayList;
    DatabaseReference reference;

    public ChatFirebaseFragment() {
        // Required empty public constructor
    }

    public ChatFirebaseFragment(String id) {
        this.id = id;
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

        View view = inflater.inflate(R.layout.fragment_chat_firebase, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {
        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (messageEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please write a message to send", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    Message message1 = new Message(PreferencesUtils.getUser(getContext()).getId().toString(), id, messageEditText.getText().toString(), String.valueOf(date.getTime()));

                    if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")){

                        database = FirebaseDatabase.getInstance();
                        database.getReference().child("chat").child(PreferencesUtils.getUser(getContext()).getId().toString()).child(id).child("messages").push()
                                .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        messageEditText.setText("");
                                    }
                                });
                    } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){

                        database = FirebaseDatabase.getInstance();
                        database.getReference().child("chat").child(id).child(PreferencesUtils.getUser(getContext()).getId().toString()).child("messages").push()
                                .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        messageEditText.setText("");
                                    }
                                });

                    }

                }

            }
        });

    }

    private void initials(View view) {
        chatRecyclerView = view.findViewById(R.id.chat_recyclerview);
        sendIcon = view.findViewById(R.id.send_icon);
        messageEditText = view.findViewById(R.id.message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        messageArrayList = new ArrayList<>();

        MessagesAdapter messagesAdapter = new MessagesAdapter(getContext(), messageArrayList);
        chatRecyclerView.setAdapter(messagesAdapter);

        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")){
            reference = database.getReference().child("chat").child(PreferencesUtils.getUser(getContext()).getId().toString()).child(id).child("messages");
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
            reference = database.getReference().child("chat").child(id).child(PreferencesUtils.getUser(getContext()).getId().toString()).child("messages");
        }

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    reference.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if necessary
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message1 = dataSnapshot.getValue(Message.class);
                    messageArrayList.add(message1);
                }

                messagesAdapter.notifyDataSetChanged();
                try {
                    chatRecyclerView.smoothScrollToPosition(chatRecyclerView.getAdapter().getItemCount() - 1);
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}