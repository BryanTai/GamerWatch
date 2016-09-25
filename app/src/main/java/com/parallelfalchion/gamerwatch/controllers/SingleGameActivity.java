package com.parallelfalchion.gamerwatch.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Bryan on 9/24/2016.
 */

public class SingleGameActivity extends AppCompatActivity {

    //TODO need to pass in the selected Game somehow
    Game game;
    private static final String GAME_CHILD = "game";
    private static final String AMAZON = "amazon";
    private static String SINGLE_GAME = "";
    private static List<String> pricesList = new ArrayList<String>();
    private DatabaseReference mFirebaseDatabaseReference;

    RadioGroup platformChoice;
    ListView vendors;
    ImageView cover;
    TextView text;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_game);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            SINGLE_GAME = extras.getString("TITLE");
        }

        platformChoice = (RadioGroup) findViewById(R.id.platformGroup);
        vendors  = (ListView) findViewById(R.id.vendorList);
        cover = (ImageView) findViewById(R.id.gameImage);
        text = (TextView) findViewById(R.id.gameTitle);

        text.setText(SINGLE_GAME);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child(AMAZON).orderByKey().equalTo(SINGLE_GAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newGame = (Map<String, Object>) dataSnapshot.getValue();
               // pricesList.add((String) newGame.get("price"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                pricesList );

        vendors.setAdapter(arrayAdapter);
    }


    //converts cover art to a byte array TODO: get this to work
    private byte[] getBytesFromFile(String path){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(classLoader.getResource(path).getFile());
            byte[] b = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            return b;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

    //TODO add a listener to the RadioButtons to handle which platform to show

}
