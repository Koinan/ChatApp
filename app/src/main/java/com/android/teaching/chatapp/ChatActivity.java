package com.android.teaching.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ArrayList<ChatData> mensajess = new ArrayList<>();
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar myToolBar = findViewById(R.id.toolbar_1);




        setSupportActionBar(myToolBar);
        getSupportActionBar().setTitle("ChatApp");
        ListView listview = findViewById(R.id.listview_1);
        myAdapter = new MyAdapter();
        listview.setAdapter (new MyAdapter());
        listview.setEmptyView(findViewById(R.id.list_item));

        String url = "https://chatapp-1cf6d.firebaseio.com/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(url);


        DatabaseReference referencia = database.getReference("messages");
        referencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot gameSnapshot:dataSnapshot.getChildren())
                {
                    ChatData value = gameSnapshot.getValue(ChatData.class);

                    mensajess.add(value);
                    Log.d("usuario", "resultado2: " + value.getUsername());
                    Log.d("texto", "resultado: " + value.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private class MyAdapter extends BaseAdapter {

        //Con ésta clase creo el adaptador, que hereda de Baseadapter
        @Override
        public int getCount() {
            return mensajess.size();
        }
        //Count es el numero de items que tendrá, si tiene mas de la cuenta la aplicación se cierra
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Éste método "infla" la XML con los datos


            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item, parent, false);
            //Situo los iconos y los nombres y les añado una posición en la lista
            TextView textView = rowView.findViewById(R.id.textomensajes);
            textView.setText(mensajess.get(position).getText());

            TextView textView2 = rowView.findViewById(R.id.textohint);
            textView.setText(mensajess.get(position).getUsername());

            return rowView;

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.botom1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int identificador = item.getItemId();
        switch (identificador) {
            case R.id.action_user:
                Intent intent = new Intent(this, NewMessageActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
