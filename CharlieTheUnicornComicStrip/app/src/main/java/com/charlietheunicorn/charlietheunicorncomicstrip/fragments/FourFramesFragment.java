package com.charlietheunicorn.charlietheunicorncomicstrip.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlietheunicorn.charlietheunicorncomicstrip.R;

/**
 * Created by Admin on 1/12/2016.
 */
public class FourFramesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.four_frame_view, container, false);
        Bundle args = getArguments();


        return rootView;
    }
}
