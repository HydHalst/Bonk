package com.ncs.projecteranking;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ncs.projecteranking.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity
        implements View.OnClickListener {

    TextInputEditText txtUsername;
    ImageButton btnCamera, btnDelete;
    ImageView imgAvatar;
    FloatingActionButton faBtnSave;

    SharedPreferences sharedPref;

    private String IMAGE_PATH;
    private static final String IMAGE_NAME = "avatar.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustaments);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        findViews();
        setClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPref.getString("username", "Username");
        txtUsername.setText(username);
        imgAvatar.setImageBitmap(loadImageFromStorage(IMAGE_PATH + "/" + IMAGE_NAME));
        Log.d("ncs", "ON_RESUME: " + username);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ncs", "ON_PAUSE");
    }

    /**
     * Finds the view for each component of the layout
     */
    private void findViews() {
        txtUsername = findViewById(R.id.txtUsername);
        btnCamera = findViewById(R.id.btnCamera);
        btnDelete = findViewById(R.id.btnDelete);
        imgAvatar = findViewById(R.id.imgAvatar);
        faBtnSave = findViewById(R.id.faBtnSave);
    }

    /**
     * Functions for each button
     *
     * @param v is the View which was clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.btnDelete:
                Log.d("ncs", "Btn Delete Pressed");
                break;
            case R.id.faBtnSave:
                SharedPreferences.Editor editor = sharedPref.edit();
                String username = txtUsername.getText().toString();
                editor.putString("username", username);
                editor.apply();
                finish();
                Log.d("ncs", "Btn Save Pressed with USERNAME: " + username);
                break;
            default:
                break;
        }
    }

    /**
     * Set click listeners for the buttons
     */
    private void setClickListeners() {
        btnCamera.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        faBtnSave.setOnClickListener(this);
    }

    /**
     * Function to crop and put the new image into imgAvatar
     *
     * @param requestCode Code of request
     * @param resultCode  Code of result
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap bitmapCrop;
            if (imageBitmap.getWidth() >= imageBitmap.getHeight()) {
                bitmapCrop = Bitmap.createBitmap(
                        imageBitmap,
                        imageBitmap.getWidth() / 2 - imageBitmap.getHeight() / 2,
                        0,
                        imageBitmap.getHeight(),
                        imageBitmap.getHeight()
                );
            } else {
                bitmapCrop = Bitmap.createBitmap(
                        imageBitmap,
                        0,
                        imageBitmap.getHeight() / 2 - imageBitmap.getWidth() / 2,
                        imageBitmap.getWidth(),
                        imageBitmap.getWidth()
                );
            }
            imgAvatar.setImageBitmap(bitmapCrop);

            IMAGE_PATH = saveToInternalStorage(bitmapCrop);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
//        path to /data/data/AndroidProyecto/app_data/avatar
        File directory = contextWrapper.getDir("avatar", Context.MODE_PRIVATE);
//        Create avatar
        File file = new File(directory, IMAGE_NAME);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path, IMAGE_NAME);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
