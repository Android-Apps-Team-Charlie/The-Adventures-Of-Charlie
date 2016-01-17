package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        LinearLayout layout = (LinearLayout)findViewById(R.id.drawing_layout);
    }
}
