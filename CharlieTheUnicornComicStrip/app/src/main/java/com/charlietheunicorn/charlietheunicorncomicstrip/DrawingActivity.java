package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.charlietheunicorn.charlietheunicorncomicstrip.shared.DrawingCanvasView;

import java.util.UUID;

public class DrawingActivity extends AppCompatActivity implements OnClickListener {
    //custom drawing view
    private DrawingCanvasView drawingCanvasView;
    //buttons
    private ImageButton currPaint, backgroundBtn, drawBtn, eraseBtn, newBtn, saveBtn;
    //sizes
    private float smallBrush, mediumBrush, largeBrush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        //get drawing view
        drawingCanvasView = (DrawingCanvasView)findViewById(R.id.body);

        //get the palette and first color button
        View footer = findViewById(R.id.footer);
        LinearLayout paintLayout = (LinearLayout)footer;
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.color_pressed));

        //sizes from dimensions
        smallBrush = 10;
        mediumBrush = 20;
        largeBrush = 30;
        //largeBrush = getResources().getInteger(R.integer.large_size);

        //draw button
        drawBtn = (ImageButton)findViewById(R.id.size_btn);
        drawBtn.setOnClickListener(this);

        //background button
        backgroundBtn = (ImageButton)findViewById(R.id.background_btn);
        backgroundBtn.setOnClickListener(this);

        //set initial size
        drawingCanvasView.setBrushSize(mediumBrush);

        //erase button
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
    }

    //user clicked paint
    public void paintClicked(View view){
        String viewToString;

        //set erase false
        drawingCanvasView.setErase(false);
        drawingCanvasView.setBrushSize(drawingCanvasView.getLastBrushSize());

        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;

            viewToString = view.getTag().toString();

            String pattern = view.getTag().toString();
            drawingCanvasView.setColor(pattern);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.color_pressed));
            //currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    @Override
    public void onClick(View view){
        final Canvas canvas = new Canvas();
        //final Rect rect = new Rect();

        if(view.getId()==R.id.size_btn){
            //draw button clicked
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_size_popup);
            //listen for clicks on size buttons
            //ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
            //smallBtn.setOnClickListener(new OnClickListener(){
            //    @Override
            //    public void onClick(View v) {
            //        drawingCanvasView.setErase(false);
            //        drawingCanvasView.setBrushSize(smallBrush);
            //        drawingCanvasView.setLastBrushSize(smallBrush);
            //        brushDialog.dismiss();
            //    }
            //});

            brushDialog.show();
        }
        else if(view.getId()==R.id.background_btn){

            final Dialog backgroundDialog = new Dialog(this);
            backgroundDialog.setTitle("Set Background:");
            backgroundDialog.setContentView(R.layout.background_popup);
            //listen for clicks on size buttons

            backgroundDialog.show();
        }
        else if(view.getId()==R.id.erase_btn){
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_size_popup);
            //size buttons

            //TODO: from size chooser
            brushDialog.show();
        }
        else if(view.getId()==R.id.new_btn){
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawingCanvasView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else if(view.getId()==R.id.save_btn){
            //save drawing
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save drawing");
            saveDialog.setMessage("Save drawing to device Gallery?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //save drawing
                    drawingCanvasView.setDrawingCacheEnabled(true);
                    ContentResolver cr = getContentResolver();
                    Bitmap dv = drawingCanvasView.getDrawingCache();
                    String state = Environment.getExternalStorageState();
                    String mediaState = Environment.MEDIA_MOUNTED;

                    if (Environment.MEDIA_MOUNTED.equals(mediaState)) {
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(),
                                drawingCanvasView.getDrawingCache(),
                                UUID.randomUUID().toString() + ".png", "drawing");
                        //feedback
                        if (imgSaved != null) {
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            savedToast.show();
                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        drawingCanvasView.destroyDrawingCache();
                    }
                    //attempt to save

                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }
}

