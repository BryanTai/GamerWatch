package com.parallelfalchion.gamerwatch.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.parallelfalchion.gamerwatch.helpers.FirebaseHelper;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.google.firebase.database.ValueEventListener;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.helpers.CustomBaseAdapter;
import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.models.Platform;
import static android.support.v4.app.ActivityCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    ListView featuredGames;

    private static final String GAME_CHILD = "game";
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

        mFirebaseDatabaseReference.child(GAME_CHILD).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
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

        //---------------------------------------------TEST DATA --------------------------------------------------------------------------------
//        Map<String, Game> gameList = new HashMap<>();
//        Game overwatch = new Game("Overwatch (PC)", 40, "https://upload.wikimedia.org/wikipedia/en/8/8f/Overwatch_cover_art_%28PC%29.jpg", Platform.PC);
//        gameList.put("Overwatch (PC)",overwatch);
//
//        Game fireEmblem = new Game("Fire Emblem: Awakening (3DS)", 50, "https://upload.wikimedia.org/wikipedia/en/4/44/Fire_Emblem_Awakening_box_art.png", Platform.THREEDS);
//        gameList.put("Fire Emblem: Awakening (3DS)", fireEmblem);
//
//        Game counterStrike = new Game("Counter Strike: Global Offensive (PC)", 10, "https://upload.wikimedia.org/wikipedia/en/c/ce/Counter-Strike_Global_Offensive.jpg", Platform.PC);
//        gameList.put("Counter Strike: Global Offensive (PC)", counterStrike);

        //Adding games to View
        //mFirebaseDatabaseReference.child(GAME_CHILD).setValue(gameList);

        featuredGames.setAdapter(customBaseAdapter);

    }



    public void startSingleGameActivity(View view){
        TextView textView = (TextView) view.findViewById(R.id.row_game_title);

        Intent intent = new Intent(this, SingleGameActivity.class);
        intent.putExtra("TITLE", textView.getText());
        startActivity(intent);
    }

}
