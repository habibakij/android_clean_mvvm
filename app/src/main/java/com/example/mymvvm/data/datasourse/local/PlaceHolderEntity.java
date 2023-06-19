package com.example.mymvvm.data.datasourse.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"ID"},unique = true)}, tableName = "PlaceHolderEntity")
public class PlaceHolderEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UID")
    private int UID;
    @ColumnInfo(name = "ID")
    private final int ID;
    @ColumnInfo(name = "userID")
    private final int userID;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "status")
    private final String status;

    public PlaceHolderEntity(int ID, int userID, String title, String status) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.status = status;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getID() {
        return ID;
    }
    public int getUserID() {
        return userID;
    }
    public String getTitle() {
        return title;
    }
    public String getStatus() {
        return status;
    }
}

