package com.parallelfalchion.gamerwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String GAME_CHILD = "game";
    private DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

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

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(overwatchID.toString());
        Game fireEmblem = new Game("Fire Emblem: Awakening", 50, "https://upload.wikimedia.org/wikipedia/en/8/8f/Overwatch_cover_art_%28PC%29.jpg", Platform.THREEDS);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(overwatchID.toString());
        Game counterStrike = new Game("Counter Strike: Global Offensive", 10, "https://upload.wikimedia.org/wikipedia/en/8/8f/Overwatch_cover_art_%28PC%29.jpg", Platform.PC);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);
    }



}
