package com.ncs.projecteranking;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncs.projecteranking.R;

public class AboutActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanta);

        tvUsername = findViewById(R.id.tvAboutText);
        imgAvatar = findViewById(R.id.imgAvatar);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPref.getString("username", "Username");
        Log.d("ncs", username);
        tvUsername.setText(String.format(this.getString(R.string.quantaText), username));
    }
}
