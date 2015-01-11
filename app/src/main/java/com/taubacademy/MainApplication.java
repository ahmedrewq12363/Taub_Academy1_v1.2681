package com.taubacademy;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Ahmed on 1/9/2015.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Tutor.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Pair.class);
        ParseObject.registerSubclass(PairRatings.class);
        Parse.initialize(this, "QsQezpRGVsuUMjw3zaj57cHlrSGUlKkHKzfnVIxv", "gqqgjaBFS19B2Y0uvpNQ0A8RSY13xhk1u5ObE9tb");
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
        ParseUser.enableAutomaticUser();


    }
}
