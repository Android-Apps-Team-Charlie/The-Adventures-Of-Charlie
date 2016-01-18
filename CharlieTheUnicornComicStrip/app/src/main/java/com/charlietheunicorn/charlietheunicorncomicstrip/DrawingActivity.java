package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.charlietheunicorn.charlietheunicorncomicstrip.shared.DrawingCanvasView;
import com.charlietheunicorn.charlietheunicorncomicstrip.shared.MovableImageView;

import java.io.ByteArrayOutputStream;

public class DrawingActivity extends AppCompatActivity implements OnClickListener {

    private DrawingCanvasView drawingCanvasView;

    private MovableImageView movableImage;
	
    // buttons
    private ImageButton currPaint, backgroundBtn, shapesBtn, sizeBtn, eraseBtn, newBtn, saveBtn, colorsBtn, applyBtn, declineBtn;

    // brush colors
    private View footer;

    // main toolbar
    private View header;

    // shape confirmation toolbar
    private View confirmation;

    private Boolean hasSelectedShape, hasSelectedBackground;

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

        header = findViewById(R.id.header);
        confirmation = findViewById(R.id.confirmation);
        hasSelectedShape = false;

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

        //region Toolbar Button Event registering
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

        applyBtn = (ImageButton)findViewById(R.id.apply_btn);
        applyBtn.setOnClickListener(this);

        declineBtn = (ImageButton)findViewById(R.id.decline_btn);
        declineBtn.setOnClickListener(this);
        //endregion

        //region Dialogs initialization
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
                if (hasSelectedShape) {
                    movableImage.setVisibility(View.VISIBLE);
                    confirmation.setVisibility(View.VISIBLE);
                    header.setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.INVISIBLE);
                }
            }
        });
        //endregion

        //region Brush Size Buttons Events
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
        //endregion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

    public void shapeClicked(View view) {
        hasSelectedShape = true;

        ImageButton imgView = (ImageButton)view;
        movableImage.setImageDrawable(imgView.getDrawable());
        movableImage.setVisibility(View.VISIBLE);
        movableImage.invalidate();
        movableImage.requestLayout();

        shapePickerDialog.dismiss();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void backgroundClicked(View view) {
        hasSelectedBackground = true;

        ImageButton imgView = (ImageButton)view;
        drawingCanvasView.setBackground(imgView.getDrawable());

        backgroundPickerDialog.dismiss();
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.size_btn) {
            brushSizeDialog.setTitle("Brush size:");

            brushSizeDialog.show();
        }
        // open background picker dialog
        else if(view.getId() == R.id.background_btn) {
            backgroundPickerDialog.show();
        }
        // open shape picker dialog
        else if(view.getId() == R.id.shape_btn) {
            shapePickerDialog.show();
        }
        // confirm shape modifications and apply to canvas
        else if(view.getId() == R.id.apply_btn) {
            hasSelectedShape = false;

            BitmapDrawable drawable = (BitmapDrawable) movableImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            // gets movable image's final matrix in order to get its position as well, which is last to be translated
            drawingCanvasView.drawBitmap(bitmap, movableImage.getFinalMatrix());

            // TODO: reset movableImage layout
            movableImage.setVisibility(View.INVISIBLE);
            confirmation.setVisibility(View.INVISIBLE);
            header.setVisibility(View.VISIBLE);

            drawingCanvasView.invalidate();
        }
        // decline any modifications to shape and return to drawing mode
        else if(view.getId() == R.id.decline_btn) {
            hasSelectedShape = false;

            // TODO: reset movableImage layout
            movableImage.setVisibility(View.INVISIBLE);
            confirmation.setVisibility(View.INVISIBLE);
            header.setVisibility(View.VISIBLE);

            drawingCanvasView.invalidate();
        }
        // toggle color palette
        else if(view.getId() == R.id.colors_btn){
            int currentVisibility = footer.getVisibility();

            if (currentVisibility == View.INVISIBLE){
                footer.setVisibility(View.VISIBLE);
            }
            else {
                footer.setVisibility((View.INVISIBLE));
            }
        }
        // toggle erase mode
        else if(view.getId() == R.id.erase_btn) {
            drawingCanvasView.setErase(true);
			
            brushSizeDialog.setTitle("Eraser size:");

            brushSizeDialog.show();
        }
        // clear canvas
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
        // sends user to save activity
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

