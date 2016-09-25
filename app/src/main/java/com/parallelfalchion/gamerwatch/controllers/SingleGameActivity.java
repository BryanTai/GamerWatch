package com.parallelfalchion.gamerwatch.controllers;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.parallelfalchion.gamerwatch.helpers.FirebaseHelper.getCoverAsDrawable;

/**
 * Created by Bryan on 9/24/2016.
 */

public class SingleGameActivity extends AppCompatActivity {

    //TODO need to pass in the selected Game somehow
    Game thisGame;

    public static final String GAME_INTENT_TAG = "gameToAddToWishlist";
    private static final String GAME_CHILD = "game";
    private static String SINGLE_GAME = "";
    private List<String> pricesList = new ArrayList<String>();


    RadioGroup platformChoice;
    ListView vendors;
    ImageView cover;
    TextView titleText;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_game);

        thisGame = (Game) getIntent().getSerializableExtra(MainActivity.SELECTED_GAME_INTENT_TAG);
        SINGLE_GAME = thisGame.getTitle();
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            SINGLE_GAME = extras.getString("TITLE");
//        }

        //TODO either use platform buttons in SingleGameView or remove them entirely.
        //platformChoice = (RadioGroup) findViewById(R.id.platformGroup);

        vendors  = (ListView) findViewById(R.id.vendorList);
        cover = (ImageView) findViewById(R.id.gameImage);
        titleText = (TextView) findViewById(R.id.gameTitle);

        titleText.setText(SINGLE_GAME);
        cover.setImageDrawable(getCoverAsDrawable(thisGame.getCover()));

        pricesList.add("BestBuy   $" + thisGame.getPrice());

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                pricesList);

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

    //Handler for "Add To Wishlist" button press.
    public void addGameToWishlist(View view){
        Log.d("SINGLEGAME", "Starting Wishlist Activity!");
        Intent wishlistIntent = new Intent(this, WishlistActivity.class);
        wishlistIntent.putExtra(GAME_INTENT_TAG, thisGame);

        startActivity(wishlistIntent);
    }
}
