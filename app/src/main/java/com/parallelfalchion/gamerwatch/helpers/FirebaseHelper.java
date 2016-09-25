package com.parallelfalchion.gamerwatch.helpers;

import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.models.Platform;

import java.util.HashMap;

/**
 * Created by Bryan on 9/25/2016.
 */

public class FirebaseHelper {

    public static Game hashMapToGame(HashMap<String, Object> map){
        return new Game((String) map.get("title"),
                (Double) map.get("price"),
                (String) map.get("cover"),
                Platform.valueOf((String) map.get("platform")));
    }
}
