package com.mrvansork.contornos;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.opencv.android.OpenCVLoader;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static{
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV not loaded");
        }else{
            Log.d(TAG, "OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraOn();
            }
        });

    }

    public void PickColor(View v){
        final ImageView color_iv = findViewById(R.id.contourColor_iv);
        int color = ((ColorDrawable)color_iv.getBackground()).getColor();
        Log.i(TAG, "Color: "+color);
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                color_iv.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    public void CameraOn(){
        Intent intent = new Intent(this, CameraActivity.class);
        int colorMode = 0;

        RadioGroup color_rg = findViewById(R.id.color_rg);
        switch (color_rg.getCheckedRadioButtonId()){
            case R.id.color_rd:
                colorMode = 0;
                break;
            case R.id.gray_rd:
                colorMode = 1;
                break;
            case R.id.canny_rd:
                colorMode = 2;
                break;
            case R.id.contour_rd:
                colorMode = 3;
                break;
        }

        ImageView color_iv = findViewById(R.id.contourColor_iv);
        int color = ((ColorDrawable)color_iv.getBackground()).getColor();

        intent.putExtra("color_mode", colorMode);
        intent.putExtra("contour_color", color);

        startActivity(intent);
    }

}
