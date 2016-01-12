package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.charlietheunicorn.charlietheunicorncomicstrip.fragments.MainActivityFragment;

import java.util.Locale;

public class MainActivity extends FragmentActivity {

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);



        if (findViewById(R.id.fragment_container) != null) {

            MainActivityFragment mainActivityFragment = new MainActivityFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, mainActivityFragment)
                    .commit();
        }
    }
}