package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.charlietheunicorn.charlietheunicorncomicstrip.fragments.MainActivityFragment;

public class MainActivity extends FragmentActivity {

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