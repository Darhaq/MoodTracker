package com.example.moodtracker;

import java.time.LocalDateTime;

// Gemmer mood data
public class MoodEntry {
    private String mood;
    private String note;
    private LocalDateTime timestamp;

    public MoodEntry(String mood, String note, LocalDateTime timestamp) {
        this.mood = mood;
        this.note = note;
        this.timestamp = timestamp;
    }

    public String getMood() {
        return mood;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
