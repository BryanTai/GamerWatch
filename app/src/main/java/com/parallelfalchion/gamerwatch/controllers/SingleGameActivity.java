package com.parallelfalchion.gamerwatch.controllers;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parallelfalchion.gamerwatch.MenuHelper;
import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String SINGLE_GAME = "Until Dawn (Playstation 4)";
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

        platformChoice = (RadioGroup) findViewById(R.id.platformGroup);
        vendors  = (ListView) findViewById(R.id.vendorList);
        cover = (ImageView) findViewById(R.id.gameImage);
        text = (TextView) findViewById(R.id.gameTitle);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child(AMAZON).orderByKey().equalTo(SINGLE_GAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newGame = (Map<String, Object>) dataSnapshot.getValue();
                pricesList.add((String) newGame.get("price"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        UUID overwatchID = UUID.randomUUID();

        mFirebaseDatabaseReference.child(GAME_CHILD).setValue(overwatchID.toString());

        Game overwatch = new Game("Until Dawn (Playstation 4)", 40, "http://multimedia.bbycastatic.ca/multimedia/products/500x500/103/10372/10372019.jpg", Platform.PS4);
        mFirebaseDatabaseReference.child(GAME_CHILD).child(overwatchID.toString()).setValue(overwatch);

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
            ClassLoader classLoader = getClass().getClassLoader();
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
