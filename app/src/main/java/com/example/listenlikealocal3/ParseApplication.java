package com.example.listenlikealocal3;

import android.app.Application;

import com.example.listenlikealocal3.Model.Playlist;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Location.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("LyObzs2y5R5r7exYV58yR03jxohjE0rQCaxOUKWy")
                .clientKey("FbRLIHk973T7iAVM40zJTfWpgNVjmPBKjis4dUs1")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
