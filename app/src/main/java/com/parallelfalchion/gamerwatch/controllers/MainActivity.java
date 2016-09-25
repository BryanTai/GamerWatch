package com.parallelfalchion.gamerwatch.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.parallelfalchion.gamerwatch.helpers.FirebaseHelper;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.google.firebase.database.ValueEventListener;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.helpers.CustomBaseAdapter;
import com.parallelfalchion.gamerwatch.models.Game;

public class MainActivity extends AppCompatActivity {

    ListView featuredGames;

    public static final String SELECTED_GAME_INTENT_TAG = "selectedGame";
    private static final String GAME_CHILD = "prod";
    private static ArrayList<Game> featuredGamesList = new ArrayList<>();
    private DatabaseReference mFirebaseDatabaseReference;
    private CustomBaseAdapter customBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        populateFeaturedGames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent itemIntent = item.getIntent();

        //TODO test if this works later....
/*
        if(this.getClass().equals(itemIntent.getComponent().getClassName())){
        //don't bother running the same activity
            return true;
        }
*/

        startActivity(itemIntent);
        return true;
    }

    private void populateFeaturedGames() {
        featuredGames = (ListView) findViewById(R.id.featuredList);
        customBaseAdapter = new CustomBaseAdapter(this, featuredGamesList);

        mFirebaseDatabaseReference.child(GAME_CHILD).orderByKey().limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newGame = (Map<String, Object>) dataSnapshot.getValue();
                Iterator iterator = newGame.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry)iterator.next();
                    featuredGamesList.add(FirebaseHelper.hashMapToGame((HashMap<String, Object>) pair.getValue()));
                    iterator.remove(); // avoids a ConcurrentModificationException
                    customBaseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        featuredGames.setAdapter(customBaseAdapter);

    }

    public void startSingleGameActivity(View view){
        TextView textView = (TextView) view.findViewById(R.id.row_game_title);
        String selectedTitle = textView.getText().toString();

        //TODO Should we store featuredGamesList in a better data structure?
        Game selectedGame = null;
        for(Game g : featuredGamesList){
            if(g.getTitle().equals(selectedTitle)){
                selectedGame = g;
                break;
            }
        }

        Intent intent = new Intent(this, SingleGameActivity.class);
        intent.putExtra(SELECTED_GAME_INTENT_TAG, selectedGame);
        startActivity(intent);
    }

}
