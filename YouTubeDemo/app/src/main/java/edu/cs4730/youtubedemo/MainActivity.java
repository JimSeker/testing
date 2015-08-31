package edu.cs4730.youtubedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/*
 * Youtube demo
 *
 * Helpful references:
 * https://developers.google.com/youtube/android/player/
 *
 * a nice tutorial of how to implement it.
 * http://www.sitepoint.com/using-the-youtube-api-to-embed-video-in-an-android-app/
 *
 *
 * To get this example to work see the DeveloperKey.java file to change the API key.
 */


public class MainActivity extends AppCompatActivity{
    MainFragment myMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            myMainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myMainFragment).commit();
        }
    }
}
