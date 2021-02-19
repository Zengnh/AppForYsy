package com.rootlibs.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AppDBConfig {
//    @PrimaryKey(autoGenerate = true)
//    public int id;

//      @ColumnInfo(name = "configKey")
    @NonNull
    @PrimaryKey
    public String configKey;

    @ColumnInfo(name = "configValue")
    public String configValue;
}
