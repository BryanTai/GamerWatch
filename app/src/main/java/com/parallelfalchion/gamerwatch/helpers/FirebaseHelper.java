package com.parallelfalchion.gamerwatch.helpers;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.models.Platform;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 9/25/2016.
 */

public class FirebaseHelper {

    public static Game hashMapToGame(HashMap<String, Object> map){
        Platform platform;

        if((map.get("platform")).equals("3DS")){
            platform = Platform.THREEDS;
        }
        else{
            platform = Platform.valueOf((String) map.get("platform"));
        }

        return new Game((String) map.get("title"),
                (Double) map.get("salesPrice"),
                //(Map<String,Double>) map.get("salesPrice"),
                (String) map.get("cover"),
                platform);
    }

    public static Drawable getCoverAsDrawable(String byteString){
        byte[] b = android.util.Base64.decode(byteString, Base64.DEFAULT);
        return new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(b, 0, b.length));
    }
}
