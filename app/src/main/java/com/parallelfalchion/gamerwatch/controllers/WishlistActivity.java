package com.parallelfalchion.gamerwatch.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parallelfalchion.gamerwatch.helpers.CustomBaseAdapter;
import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Game;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.parallelfalchion.gamerwatch.controllers.MainActivity.SELECTED_GAME_INTENT_TAG;

/**
 * Created by Bryan on 9/24/2016.
 */

public class WishlistActivity extends AppCompatActivity{

    private final String SAVE_FILE_NAME = "gamerwatch_wishlist";

    private JSONArray jsonGames;
    private ArrayList<Game> savedGameList;

    private Gson gson;

    private ListView wishlistView;

    private CustomBaseAdapter customBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        gson = new Gson();
        savedGameList = new ArrayList<Game>();

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

            String data = fileContent.toString();
            if(data.isEmpty()){
                jsonGames = new JSONArray();
            }else{
                jsonGames = new JSONArray(data);
            }
        } catch (FileNotFoundException e) {
            //If there is no wishlist save file, create a new one.
            try {
                jsonGames = new JSONArray();
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

        wishlistView = (ListView) findViewById(R.id.wishlistView);
        customBaseAdapter = new CustomBaseAdapter(this, savedGameList);

        //TODO this is a goddamn mess.... refactor when not on a deadline
        for(int i = 0; i < jsonGames.length(); i++){
            JSONObject jsonRow = null;
            try {
                jsonRow = jsonGames.getJSONObject(i);
                Game gameRow = gson.fromJson(jsonRow.toString(), Game.class);
                savedGameList.add(gameRow);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        wishlistView.setAdapter(customBaseAdapter);

        if(jsonGames.length() <= 0){
            showEmptyWishlist();
        }

    }

    private void showEmptyWishlist(){
        //TODO print Empty wishlist message
        Log.d("WISHLIST", "Nothing in wishlist...");
    }

    //TODO
    private void addNewGameToWishlist(Game toAdd) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(SAVE_FILE_NAME, MODE_PRIVATE);
            JSONObject newGame = gameObjectToJsonObject(toAdd);
            jsonGames.put(newGame);
            String toStore = jsonGames.toString();
            fos.write(toStore.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    //TODO Extract this method into a helper from MainActivity, WishlistActivity, and SearchActivity
    public void startSingleGameActivity(View view){
        TextView textView = (TextView) view.findViewById(R.id.row_game_title);
        String selectedTitle = textView.getText().toString();

        Game selectedGame = null;
        for(Game g : savedGameList){
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
