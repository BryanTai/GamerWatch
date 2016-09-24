package com.parallelfalchion.gamerwatch.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.UUID;

import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.models.Platform;

public class MainActivity extends AppCompatActivity {

    ListView featuredGames;

    private static final String GAME_CHILD = "game";
    private DatabaseReference mFirebaseDatabaseReference;


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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    private void populateFeaturedGames() {
        featuredGames = (ListView) findViewById(R.id.featuredList);

        mFirebaseDatabaseReference.child(GAME_CHILD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newGame = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println(newGame.get("_title"));
                System.out.println(newGame.get("_price"));
                System.out.println(newGame.get("_platform"));
                System.out.println(newGame.get("_cover"));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        UUID overwatchID = UUID.randomUUID();
        UUID fireEmblemID = UUID.randomUUID();
        UUID counterStrikeID = UUID.randomUUID();

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(overwatchID.toString());
        Game overwatch = new Game("Overwatch", 40, "https://upload.wikimedia.org/wikipedia/en/8/8f/Overwatch_cover_art_%28PC%29.jpg", Platform.PC);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(fireEmblemID.toString());
        Game fireEmblem = new Game("Fire Emblem: Awakening", 50, "https://upload.wikimedia.org/wikipedia/en/4/44/Fire_Emblem_Awakening_box_art.png", Platform.THREEDS);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(counterStrikeID.toString());
        Game counterStrike = new Game("Counter Strike: Global Offensive", 10, "https://upload.wikimedia.org/wikipedia/en/c/ce/Counter-Strike_Global_Offensive.jpg", Platform.PC);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);
    }
}
