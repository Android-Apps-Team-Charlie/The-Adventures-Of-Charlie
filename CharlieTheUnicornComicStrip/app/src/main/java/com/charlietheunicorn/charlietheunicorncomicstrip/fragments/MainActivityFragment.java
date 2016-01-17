package com.charlietheunicorn.charlietheunicorncomicstrip.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlietheunicorn.charlietheunicorncomicstrip.DrawingActivity;
import com.charlietheunicorn.charlietheunicorncomicstrip.R;
/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View view;
    AnimationDrawable rocketAnimation;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();

        view = inflater.inflate(R.layout.fragment_main, container, false);

        view.animate().alpha(1.0f);

        ImageView rocketImage = (ImageView) view.findViewById(R.id.iv_container);
        rocketImage.setBackgroundResource(R.drawable.rocket_thrust);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/comics.ttf");

        Button button_start = (Button) view.findViewById(R.id.button_start);

        button_start.setTypeface(typeface);
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        rocketAnimation.start();
        super.onStart();
    }
}
