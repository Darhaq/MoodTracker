package com.example.moodtracker;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogMoodActivity extends AppCompatActivity {

    private EditText editNote;
    private String selectedMood = "Neutral"; // standardværdi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_mood);

        editNote = findViewById(R.id.edit_note);
        Button btnSave = findViewById(R.id.btn_save); // Finder gem-knappen

        // Find mood-knapper
        findViewById(R.id.btn_happy).setOnClickListener(v -> selectedMood = "Happy");
        findViewById(R.id.btn_sad).setOnClickListener(v -> selectedMood = "Sad");
        findViewById(R.id.btn_stressed).setOnClickListener(v -> selectedMood = "Stressed");


        // Håndterer gemning af humør med bekræftelsespopup
        btnSave.setOnClickListener(v -> {
            MoodEntry newMood = new MoodEntry(selectedMood, editNote.getText().toString(), LocalDateTime.now());
            saveMood(newMood);

            // Vis popup med "Mood saved!"
            new AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Mood saved!")
                    .setPositiveButton("OK", (dialog, which) -> finish()) // Lukker dialog og går tilbage
                    .setCancelable(false) // Forhindrer lukning ved baggrundsklik
                    .show();
        });
    }

    private void saveMood(MoodEntry newMood) {
        SharedPreferences prefs = getSharedPreferences("MoodTracker", MODE_PRIVATE); // Får adgang til SharedPreferences

        String json = prefs.getString("mood_list", "[]"); // Henter eksisterende JSON eller tom liste
        Type listType = new TypeToken<List<MoodEntry>>() {
        }.getType();
        List<MoodEntry> moods = GsonHelper.getGson().fromJson(json, listType); // Konverterer JSON til liste

        if (moods == null) {
            moods = new ArrayList<>(); // Initialiserer tom liste, hvis null
        }

        moods.add(newMood); // Tilføjer det nye humørindlæg
        String updatedJson = GsonHelper.getGson().toJson(moods); // Konverterer tilbage til JSON
        prefs.edit().putString("mood_list", updatedJson).apply(); // Gemmer opdateret JSON

        Log.d("MoodTracker", "Saved moods: " + updatedJson); // Logger gemte data
    }

    @Override
    protected void onStart() { super.onStart(); Log.d("MoodTracker", "onStart called"); }
    @Override
    protected void onResume() { super.onResume(); Log.d("MoodTracker", "onResume called"); }
    @Override
    protected void onPause() { super.onPause(); Log.d("MoodTracker", "onPause called"); }
    @Override
    protected void onStop() { super.onStop(); Log.d("MoodTracker", "onStop called"); }
    @Override
    protected void onDestroy() { super.onDestroy(); Log.d("MoodTracker", "onDestroy called"); }
}
