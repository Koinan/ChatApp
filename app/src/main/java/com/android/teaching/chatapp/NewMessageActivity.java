package com.android.teaching.chatapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

public class NewMessageActivity extends AppCompatActivity {
    private EditText messageEditText;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar myToolBar = findViewById(R.id.toolbar_1);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setTitle("New Message");
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayHomeAsUpEnabled(true);
        messageEditText = findViewById(R.id.messageedittext);
        usernameEditText = findViewById(R.id.usernameedittext);
    }
    private void setupConnection() {
        String url = "https://chatapp-1cf6d.firebaseio.com/";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referencia = database.getReference(url);
                referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("CHAT","SUCCESS!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CHAT","ERROR: " + databaseError.getMessage());
            }
        });
    }

    public void doSend(View view) {
        String message = messageEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("El mensaje no puede estar vacío");
        } else if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("El usuario no puede estar vacío");

        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference referencia = database.getReference("messages");
            referencia.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        }
    }
