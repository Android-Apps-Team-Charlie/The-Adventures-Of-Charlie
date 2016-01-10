package com.example.admin.charlietheunicorn;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Button startButton = (Button) view.findViewById(R.id.startGameBtn);


        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startButton.setBackgroundResource(R.drawable.buttonclicked);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        startButton.setBackgroundResource(R.drawable.button);
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        return view;



    }




}
