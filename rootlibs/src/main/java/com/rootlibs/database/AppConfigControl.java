package com.rootlibs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface AppConfigControl {
    @Query("SELECT * FROM AppDBConfig")
    List<AppDBConfig> selectAll();

    @Query("SELECT * FROM AppDBConfig WHERE configKey IN (:userIds)")
    List<AppDBConfig> queryByKey(String[] userIds);
//    两个参数查询
//    @Query("SELECT * FROM AppConfig WHERE configValue LIKE :first AND " +
//           "last_name LIKE :last LIMIT 1")
//    AppConfig findByName(String first, String last);

    @Query("UPDATE AppDBConfig SET configKey = :key WHERE configValue = :value")
    void update(String key, String value);
//-----------------------------------------------------------------方法二
    @Insert
    void insertAll(AppDBConfig... users);
    @Insert
    void insert(AppDBConfig info);
    @Delete
    void delete(AppDBConfig info);
    @Update
    void updata(AppDBConfig enter);
}
