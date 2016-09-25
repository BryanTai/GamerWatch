package com.parallelfalchion.gamerwatch.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.google.gson.Gson;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Game;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Bryan on 9/24/2016.
 */

public class WishlistActivity extends AppCompatActivity{

    private final String SAVE_FILE_NAME = "gamerwatch_wishlist";

    private JSONArray savedGames;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        gson = new Gson();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        loadSavedGamesFromFile();

        Intent intent = getIntent();
        //TODO check if there was a new Game passed in the Intent to add to our Wishlist
        Game toAdd = (Game) intent.getSerializableExtra(SingleGameActivity.GAME_INTENT_TAG);
        if (toAdd != null){
            addNewGameToWishlist(toAdd);
        }



        populateWishlist();
    }

    private void loadSavedGamesFromFile() {
        try {
            FileInputStream fis = openFileInput(SAVE_FILE_NAME);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            int n;
            while ((n = fis.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }

            savedGames = new JSONArray(fileContent.toString());
        } catch (FileNotFoundException e) {
            //If there is no wishlist save file, create a new one.
            try {
                openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

    private void populateWishlist() {
        //TODO
        for(int i = 0; i < savedGames.length(); i++){
            JSONObject jsonRow = null;
            try {
                jsonRow = savedGames.getJSONObject(i);
                Game gameRow = gson.fromJson(jsonRow.toString(), Game.class);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void showEmptyWishlist(){

    }

    //TODO
    private void addNewGameToWishlist(Game toAdd) {
        FileOutputStream fos = null;
        try {
            fos = this.openFileOutput(SAVE_FILE_NAME, MODE_PRIVATE);
            final OutputStreamWriter osw = new OutputStreamWriter(fos);
            JSONObject newGame = gameObjectToJsonObject(toAdd);
            savedGames.put(newGame);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showAddedPopup(toAdd);

    }
    private void showAddedPopup(Game addedGame) {
        //TODO show message that a Game got added to wishlist
        Log.d("WISHLIST", "Added a game!");
    }

    private JSONObject gameObjectToJsonObject(Game gameToStore) throws JSONException {
        String jsonInString = gson.toJson(gameToStore);
        return new JSONObject(jsonInString);
    }
}
