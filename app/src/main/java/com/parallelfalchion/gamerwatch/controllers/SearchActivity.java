package com.parallelfalchion.gamerwatch.controllers;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.parallelfalchion.gamerwatch.helpers.MenuHelper;
import com.parallelfalchion.gamerwatch.R;

/**
 * Created by Bryan on 9/24/2016.
 */

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    //TODO
    private void doMySearch(String query) {
        Log.d("LOOP","ZOOP");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

}
