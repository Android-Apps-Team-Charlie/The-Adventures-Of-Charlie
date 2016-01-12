package com.charlietheunicorn.charlietheunicorncomicstrip.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlietheunicorn.charlietheunicorncomicstrip.R;

public class TwoFramesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.two_frame_view, container, false);
        Bundle args = getArguments();


        return rootView;
    }
}
