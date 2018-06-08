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
import com.google.firebase.iid.FirebaseInstanceId;
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
        String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("device_push_token");
        databaseReference.setValue(token);
    }




    public void doSend(View view) {
        String message = messageEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("El mensaje no puede estar vacío");
        } else if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("El usuario no puede estar vacío");
        } else {
            ChatData model = new ChatData();
            model.setText(messageEditText.getText().toString());
            model.setUsername(usernameEditText.getText().toString());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("messages");
            String id = myRef.push().getKey();
            myRef.child(id).setValue(model);

            finish();
        }
        }
    }
