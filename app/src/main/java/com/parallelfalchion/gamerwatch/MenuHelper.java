package com.parallelfalchion.gamerwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parallelfalchion.gamerwatch.controllers.MainActivity;
import com.parallelfalchion.gamerwatch.controllers.SearchActivity;
import com.parallelfalchion.gamerwatch.controllers.WishlistActivity;

/**
 * Created by Bryan on 9/24/2016.
 */

public class MenuHelper {

    public static void populateMenu (Activity activity, Menu menu){
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);

        //TODO: there's gotta be a cleaner way to do this...
        MenuItem featured = menu.findItem(R.id.menu_featured);
        Intent intentFeatured = new Intent(activity, MainActivity.class);
        featured.setIntent(intentFeatured);

        MenuItem search = menu.findItem(R.id.menu_search);
        Intent intentSearch = new Intent(activity, SearchActivity.class);
        search.setIntent(intentSearch);

        MenuItem wishlist = menu.findItem(R.id.menu_wishlist);
        Intent intentWishlist = new Intent(activity, WishlistActivity.class);
        wishlist.setIntent(intentWishlist);
    }
}
