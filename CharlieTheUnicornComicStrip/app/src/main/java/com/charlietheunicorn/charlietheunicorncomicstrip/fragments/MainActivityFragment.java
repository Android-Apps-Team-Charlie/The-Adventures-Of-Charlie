package com.charlietheunicorn.charlietheunicorncomicstrip.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.charlietheunicorn.charlietheunicorncomicstrip.R;
import com.charlietheunicorn.charlietheunicorncomicstrip.fragments.StartGameFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView tx = (TextView)view.findViewById(R.id.tv_welcome);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/comics.ttf");

        tx.setTypeface(typeface);

        Button button_start = (Button) view.findViewById(R.id.button_start);

        button_start.setTypeface(typeface);
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StartGameFragment frag = new StartGameFragment();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.fragment_container, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

}
