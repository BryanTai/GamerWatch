package com.parallelfalchion.gamerwatch.controllers;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parallelfalchion.gamerwatch.helpers.CustomBaseAdapter;
import com.parallelfalchion.gamerwatch.helpers.FirebaseHelper;
import com.parallelfalchion.gamerwatch.helpers.ListViewHelper;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Bryan on 9/24/2016.
 */

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabaseReference;
    private CustomBaseAdapter customBaseAdapter;
    private static final String GAME_CHILD = "game";
    private static ArrayList<Game> searchedGamesList = new ArrayList<>();
    ListView searchedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        searchedGames = (ListView) findViewById(R.id.resultList);
        customBaseAdapter = new CustomBaseAdapter(this, searchedGamesList);

        mFirebaseDatabaseReference.child(GAME_CHILD).orderByKey().equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newGame = (Map<String, Object>) dataSnapshot.getValue();
                Iterator iterator = newGame.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry)iterator.next();
                    searchedGamesList.add(FirebaseHelper.hashMapToGame((HashMap<String, Object>) pair.getValue()));
                    iterator.remove(); // avoids a ConcurrentModificationException
                    customBaseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        searchedGames.setAdapter(customBaseAdapter);
        Log.d("SEARCH","Searching with query: " + query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

    //TODO Extract this method into a helper from MainActivity, WishlistActivity, and SearchActivity
    public void startSingleGameActivity(View view){
        ListViewHelper.startSingleGameActivity(view,this,searchedGamesList);
    }
}
