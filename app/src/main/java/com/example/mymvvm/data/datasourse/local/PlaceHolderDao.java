package com.example.mymvvm.data.datasourse.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaceHolderDao {
    @Query("SELECT * FROM PlaceHolderEntity ORDER BY ID")
    LiveData<List<PlaceHolderEntity>> getAllPlaceHolderData();

    @Insert
    void insert(PlaceHolderEntity placeHolder);

    @Update
    void update(PlaceHolderEntity placeHolder);

    @Delete
    void delete(PlaceHolderEntity placeHolder);
}

