package com.charlietheunicorn.charlietheunicorncomicstrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.UUID;

public class SaveActivity extends AppCompatActivity {

    private Button saveBtn,mmsBtn,startOverBtn;

    private Bitmap bmp;

    private String mediaState;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        context = this;

        saveBtn = (Button)findViewById(R.id.gallery_btn);
        mmsBtn = (Button)findViewById(R.id.mms_btn);
        startOverBtn = (Button)findViewById(R.id.start_over_btn);

        mediaState = Environment.MEDIA_MOUNTED;

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(bmp);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Environment.MEDIA_MOUNTED.equals(mediaState)) {
                    String imgSaved = MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            bmp,
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
                }
            }
        });

        mmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bmp,
                        UUID.randomUUID().toString() + ".png", "drawing");

                Uri bmpUri = Uri.parse(imgSaved);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra("sms_body", "some text");
                sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                sendIntent.setType("image/png");
                startActivity(sendIntent);
            }
        });

        startOverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newGame = new Intent(context, DrawingActivity.class);
                startActivity(newGame);

            }
        });
    }
}
