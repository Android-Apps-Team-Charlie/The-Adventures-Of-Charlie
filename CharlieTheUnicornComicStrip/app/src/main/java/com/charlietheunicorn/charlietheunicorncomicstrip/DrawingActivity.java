package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.charlietheunicorn.charlietheunicorncomicstrip.shared.DrawingCanvasView;
import com.charlietheunicorn.charlietheunicorncomicstrip.shared.MovableImageView;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class DrawingActivity extends AppCompatActivity implements OnClickListener {

    private DrawingCanvasView drawingCanvasView;

    private MovableImageView movableImage;
	
    // buttons
    private ImageButton currPaint, backgroundBtn, shapesBtn, sizeBtn, eraseBtn, newBtn, saveBtn, colorsBtn;

    private Context ctx = this;

    private View layout;

    // brush colors
    private View footer;

    // brush sizes
    private float xsBrush, sBrush, mBrush, lBrush, xlBrush, xxlBrush;

    private Dialog brushSizeDialog;
    private Dialog backgroundPickerDialog;
    private Dialog shapePickerDialog;

    // brush size buttons
    private ImageButton xSmallBrush, smallBrush, mediumBrush, largeBrush, xLargeBrush, xXLargeBrush;

    private void init() {
        // get canvas to operate on
        drawingCanvasView = (DrawingCanvasView)findViewById(R.id.body);
        movableImage = (MovableImageView)findViewById(R.id.movable_image);
        layout = findViewById(R.id.drawing_layout);

        //get the palette and first color button
        footer = findViewById(R.id.footer);
        LinearLayout paintLayout = (LinearLayout)footer.findViewById(R.id.drawing_toolbar_bottom_layout);
		
        xsBrush = getResources().getDimension(R.dimen.xsmall_brush_size);
        sBrush = getResources().getDimension(R.dimen.small_brush_size);
        mBrush = getResources().getDimension(R.dimen.medium_brush_size);
        lBrush = getResources().getDimension(R.dimen.large_brush_size);
        xlBrush = getResources().getDimension(R.dimen.xlarge_brush_size);
        xxlBrush = getResources().getDimension(R.dimen.xxlarge_brush_size);

        // get color palette and set brush color to first color btn
		
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.color_pressed));

        // set initial brush size to small
        drawingCanvasView.setBrushSize(sBrush);
        drawingCanvasView.setLastBrushSize(sBrush);

        // get Size btn
        sizeBtn = (ImageButton)findViewById(R.id.size_btn);
        sizeBtn.setOnClickListener(this);

        // get Background btn
        backgroundBtn = (ImageButton)findViewById(R.id.background_btn);
        backgroundBtn.setOnClickListener(this);

        // get Shapes btn
        shapesBtn = (ImageButton)findViewById(R.id.shape_btn);
        shapesBtn.setOnClickListener(this);

        // get Erase btn
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        // get New Drawing btn
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        // get Save btn
        saveBtn = (ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        //colors button
        colorsBtn = (ImageButton)findViewById(R.id.colors_btn);
        colorsBtn.setOnClickListener(this);
		
        // set brushSizeDialog
        brushSizeDialog = new Dialog(this);
        brushSizeDialog.setTitle("Brush size:");
        brushSizeDialog.setContentView(R.layout.brush_size_popup);

        // set backgroundPickerDialog
        backgroundPickerDialog = new Dialog(this);
        backgroundPickerDialog.setTitle("Set Background:");
        backgroundPickerDialog.setContentView(R.layout.background_popup);

        // set shapePickerDialog
        shapePickerDialog = new Dialog(this);
        shapePickerDialog.setTitle("Choose cartoon:");
        shapePickerDialog.setContentView(R.layout.shapes_popup);
        shapePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                movableImage.setVisibility(View.VISIBLE);
                findViewById(R.id.confirmation).setVisibility(View.VISIBLE);
                findViewById(R.id.header).setVisibility(View.INVISIBLE);

                BitmapDrawable drawable = (BitmapDrawable) movableImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                drawingCanvasView.drawBitmap(bitmap, movableImage.getMatrix());
                
                movableImage.setVisibility(View.INVISIBLE);
                findViewById(R.id.confirmation).setVisibility(View.INVISIBLE);
                findViewById(R.id.header).setVisibility(View.VISIBLE);

                drawingCanvasView.invalidate();
            }
        });


        // get brushSize btns
        xSmallBrush = (ImageButton)brushSizeDialog.findViewById(R.id.xsmall_brush);
        smallBrush = (ImageButton)brushSizeDialog.findViewById(R.id.small_brush);
        mediumBrush = (ImageButton)brushSizeDialog.findViewById(R.id.medium_brush);
        largeBrush = (ImageButton)brushSizeDialog.findViewById(R.id.large_brush);
        xLargeBrush = (ImageButton)brushSizeDialog.findViewById(R.id.xlarge_brush);
        xXLargeBrush = (ImageButton)brushSizeDialog.findViewById(R.id.xxlarge_brush);

        // initialize brush size btn event listeners
        xSmallBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // drawingCanvasView.setErase(false);
                drawingCanvasView.setBrushSize(xsBrush);
                drawingCanvasView.setLastBrushSize(xsBrush);
                brushSizeDialog.dismiss();
            }
        });

        smallBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                drawingCanvasView.setBrushSize(sBrush);
                drawingCanvasView.setLastBrushSize(sBrush);
                brushSizeDialog.dismiss();
            }
        });

        mediumBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                drawingCanvasView.setBrushSize(mBrush);
                drawingCanvasView.setLastBrushSize(mBrush);
                brushSizeDialog.dismiss();
            }
        });

        largeBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                drawingCanvasView.setBrushSize(lBrush);
                drawingCanvasView.setLastBrushSize(lBrush);
                brushSizeDialog.dismiss();
            }
        });

        xLargeBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                drawingCanvasView.setBrushSize(xlBrush);
                drawingCanvasView.setLastBrushSize(xlBrush);
                brushSizeDialog.dismiss();
            }
        });

        xXLargeBrush.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                drawingCanvasView.setBrushSize(xxlBrush);
                drawingCanvasView.setLastBrushSize(xxlBrush);
                brushSizeDialog.dismiss();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        init();
    }

    public void paintClicked(View view){
        drawingCanvasView.setErase(false);
        drawingCanvasView.setBrushSize(drawingCanvasView.getLastBrushSize());

        if(view != currPaint){
            ImageButton imgView = (ImageButton)view;

            String color = view.getTag().toString();

            drawingCanvasView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.color_pressed));

            currPaint = (ImageButton)view;
        }
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.size_btn) {
            brushSizeDialog.setTitle("Brush size:");

            brushSizeDialog.show();
        }
        else if(view.getId() == R.id.background_btn) {
            backgroundPickerDialog.show();
        }
        else if(view.getId() == R.id.shape_btn) {
            shapePickerDialog.show();
        }
        else if(view.getId()==R.id.colors_btn){
            int currentVisibility = footer.getVisibility();

            if (currentVisibility == View.INVISIBLE){
                footer.setVisibility(View.VISIBLE);
            }
            else{
                footer.setVisibility((View.INVISIBLE));
            }
        }
        else if(view.getId() == R.id.erase_btn) {
            drawingCanvasView.setErase(true);
			
            brushSizeDialog.setTitle("Eraser size:");

            brushSizeDialog.show();
        }
        else if(view.getId() == R.id.new_btn) {
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
        else if(view.getId() == R.id.save_btn) {

            drawingCanvasView.setDrawingCacheEnabled(true);
            Bitmap currentDrawing = drawingCanvasView.getDrawingCache();

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            currentDrawing.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent = new Intent(this, SaveActivity.class);
            intent.putExtra("image",byteArray);

            drawingCanvasView.destroyDrawingCache();
            startActivity(intent);
        }
    }
}

