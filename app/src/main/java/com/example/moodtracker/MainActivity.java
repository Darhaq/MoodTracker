package com.example.moodtracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d("MoodTracker", "onCreate called"); // Logger, når skærmen oprettes

        // Eventhandlers for knapper
        findViewById(R.id.btn_log_mood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogMoodActivity.class));
            }
        });
        findViewById(R.id.btn_view_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
        findViewById(R.id.btn_resources).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mentalhealth.org")));
            }
        });

        // Justerer padding for systembjælker (f.eks. statusbjælke)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MoodTracker", "onStart called"); // Logger, når aktiviteten starter
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MoodTracker", "onResume called"); // Logger, når aktiviteten genoptages
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MoodTracker", "onPause called"); // Logger, når aktiviteten bliver delvist usynlig
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MoodTracker", "onStop called"); // Logger, når aktiviteten bliver helt usynlig
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MoodTracker", "onDestroy called"); // Logger, når aktiviteten lukkes
    }
}