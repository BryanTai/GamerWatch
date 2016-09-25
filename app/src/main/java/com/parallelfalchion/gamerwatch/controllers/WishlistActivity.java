package com.parallelfalchion.gamerwatch.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.parallelfalchion.gamerwatch.MenuHelper;
import com.parallelfalchion.gamerwatch.R;

/**
 * Created by Bryan on 9/24/2016.
 */

public class WishlistActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);


        populateWishlist();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.populateMenu(this, menu);
        return true;
    }

    private void populateWishlist() {
        //TODO
    }
}