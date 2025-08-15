package com.example.moodtracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

// hjaelper med at serialisere MoodEntry data
public class GsonHelper {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
        }
        return gson;
    }
}
