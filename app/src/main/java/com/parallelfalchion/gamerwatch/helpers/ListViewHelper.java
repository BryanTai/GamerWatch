package com.parallelfalchion.gamerwatch.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.controllers.SingleGameActivity;
import com.parallelfalchion.gamerwatch.models.Game;

import java.util.List;



/**
 * Created by Bryan on 9/25/2016.
 */

public class ListViewHelper {

    public static final String SELECTED_GAME_INTENT_TAG = "selectedGame";
    public static void startSingleGameActivity(View view, Context context, List<Game> gameList){
        TextView textView = (TextView) view.findViewById(R.id.row_game_title);
        String selectedTitle = textView.getText().toString();

        //TODO Should we store featuredGamesList in a better data structure?
        Game selectedGame = null;
        for(Game g : gameList){
            if(g.getTitle().equals(selectedTitle)){
                selectedGame = g;
                break;
            }
        }

        Intent intent = new Intent(context, SingleGameActivity.class);
        intent.putExtra(SELECTED_GAME_INTENT_TAG, selectedGame);
        context.startActivity(intent);
    }

}
