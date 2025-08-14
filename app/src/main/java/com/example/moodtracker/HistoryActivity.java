package com.example.moodtracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TextView textViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textViewHistory = findViewById(R.id.tv_mood_list);
        displayHistory();
    }

    private List<MoodEntry> loadMoods() {
        SharedPreferences prefs = getSharedPreferences("MoodTracker", MODE_PRIVATE);
        String json = prefs.getString("mood_list", null);

        if (json == null) {
            return new ArrayList<>();
        }

        try {
            Type listType = new TypeToken<List<MoodEntry>>(){}.getType();
            List<MoodEntry> moods = GsonHelper.getGson().fromJson(json, listType);
            if (moods != null) {
                return moods;
            }
        } catch (Exception e) {
            Log.w("MoodTracker", "Not a JSON array, trying migration...", e);
        }

        try {
            MoodEntry singleMood = GsonHelper.getGson().fromJson(json, MoodEntry.class);
            if (singleMood != null) {
                List<MoodEntry> migrated = new ArrayList<>();
                migrated.add(singleMood);
                prefs.edit().putString("mood_list", GsonHelper.getGson().toJson(migrated)).apply();
                Log.d("MoodTracker", "Migrated old data to array format.");
                return migrated;
            }
        } catch (Exception e) {
            Log.e("MoodTracker", "Migration failed: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void displayHistory() {
        List<MoodEntry> moods = loadMoods();
        if (moods.isEmpty()) {
            textViewHistory.setText("No moods logged yet");
            return;
        }

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (MoodEntry mood : moods) {
            sb.append(mood.getMood())
                    .append(" - ")
                    .append(mood.getTimestamp().format(formatter));

            if (mood.getNote() != null && !mood.getNote().isEmpty()) {
                sb.append(" (Note: ").append(mood.getNote()).append(")");
            }

            sb.append("\n");
        }

        textViewHistory.setText(sb.toString());
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
