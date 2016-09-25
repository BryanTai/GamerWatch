package com.parallelfalchion.gamerwatch.helpers;

import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.models.Platform;

import java.util.HashMap;

/**
 * Created by Bryan on 9/25/2016.
 */

public class FirebaseHelper {

    public static Game hashMapToGame(HashMap<String, Object> map){
        return new Game((String) map.get("_title"),
                (long) map.get("_price"),
                (String) map.get("_cover"),
                Platform.valueOf((String) map.get("_platform")));
    }
}
