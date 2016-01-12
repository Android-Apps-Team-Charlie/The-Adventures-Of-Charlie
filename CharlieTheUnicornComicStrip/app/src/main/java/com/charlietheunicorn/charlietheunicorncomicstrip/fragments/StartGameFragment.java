package com.charlietheunicorn.charlietheunicorncomicstrip.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.charlietheunicorn.charlietheunicorncomicstrip.R;

public class StartGameFragment extends Fragment {

    View view;
    String str;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        view = inflater.inflate(R.layout.choose_view, container, false);
        final Bundle args = this.getArguments();

        Button button_continue = (Button) view.findViewById(R.id.btn_continue);
        button_continue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int id = ((RadioGroup)view.findViewById(R.id.rb_group)).getCheckedRadioButtonId();

                int checkedButton = getCheckedRadioButton(id);

                redirectToFrame(checkedButton);


            }
        });

        return view;
    }

    private int getCheckedRadioButton( int id ) {
        int checkedButton = 0;
        switch (id) {
            case R.id.radioButton:
                checkedButton = 1;
                break;
            case R.id.radioButton2:
                checkedButton = 2;
                break;
            case R.id.radioButton3:
                checkedButton = 3;
                break;
        }
        return checkedButton;
    }

    private void redirectToFrame(int checkedButton){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment frag;

        switch (checkedButton) {
            case 1:
                frag = new TwoFramesFragment();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.fragment_container, frag);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 2:
                frag = new ThreeFramesFragment();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.fragment_container, frag);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:

                frag = new FourFramesFragment();
                ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.fragment_container, frag);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }
}
