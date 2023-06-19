package com.example.mymvvm.data.datasourse.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PlaceHolderEntity.class}, version = 1, exportSchema = false)
public abstract class PlaceHolderDatabase extends RoomDatabase {
    private static PlaceHolderDatabase instance;
    public abstract PlaceHolderDao placeHolderDao();
    public static synchronized PlaceHolderDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PlaceHolderDatabase.class, "placeholder_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

