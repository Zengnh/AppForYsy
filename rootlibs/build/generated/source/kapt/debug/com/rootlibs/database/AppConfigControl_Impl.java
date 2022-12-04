package com.rootlibs.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppConfigControl_Impl implements AppConfigControl {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppDBConfig> __insertionAdapterOfAppDBConfig;

  private final EntityDeletionOrUpdateAdapter<AppDBConfig> __deletionAdapterOfAppDBConfig;

  private final EntityDeletionOrUpdateAdapter<AppDBConfig> __updateAdapterOfAppDBConfig;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public AppConfigControl_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppDBConfig = new EntityInsertionAdapter<AppDBConfig>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `AppDBConfig` (`configKey`,`configValue`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppDBConfig value) {
        if (value.configKey == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.configKey);
        }
        if (value.configValue == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.configValue);
        }
      }
    };
    this.__deletionAdapterOfAppDBConfig = new EntityDeletionOrUpdateAdapter<AppDBConfig>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `AppDBConfig` WHERE `configKey` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppDBConfig value) {
        if (value.configKey == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.configKey);
        }
      }
    };
    this.__updateAdapterOfAppDBConfig = new EntityDeletionOrUpdateAdapter<AppDBConfig>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `AppDBConfig` SET `configKey` = ?,`configValue` = ? WHERE `configKey` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppDBConfig value) {
        if (value.configKey == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.configKey);
        }
        if (value.configValue == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.configValue);
        }
        if (value.configKey == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.configKey);
        }
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE AppDBConfig SET configKey = ? WHERE configValue = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final AppDBConfig... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAppDBConfig.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final AppDBConfig info) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAppDBConfig.insert(info);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final AppDBConfig info) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAppDBConfig.handle(info);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updata(final AppDBConfig enter) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAppDBConfig.handle(enter);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final String key, final String value) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    if (key == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, key);
    }
    _argIndex = 2;
    if (value == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, value);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public List<AppDBConfig> selectAll() {
    final String _sql = "SELECT * FROM AppDBConfig";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfConfigKey = CursorUtil.getColumnIndexOrThrow(_cursor, "configKey");
      final int _cursorIndexOfConfigValue = CursorUtil.getColumnIndexOrThrow(_cursor, "configValue");
      final List<AppDBConfig> _result = new ArrayList<AppDBConfig>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AppDBConfig _item;
        _item = new AppDBConfig();
        if (_cursor.isNull(_cursorIndexOfConfigKey)) {
          _item.configKey = null;
        } else {
          _item.configKey = _cursor.getString(_cursorIndexOfConfigKey);
        }
        if (_cursor.isNull(_cursorIndexOfConfigValue)) {
          _item.configValue = null;
        } else {
          _item.configValue = _cursor.getString(_cursorIndexOfConfigValue);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<AppDBConfig> queryByKey(final String[] userIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM AppDBConfig WHERE configKey IN (");
    final int _inputSize = userIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : userIds) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfConfigKey = CursorUtil.getColumnIndexOrThrow(_cursor, "configKey");
      final int _cursorIndexOfConfigValue = CursorUtil.getColumnIndexOrThrow(_cursor, "configValue");
      final List<AppDBConfig> _result = new ArrayList<AppDBConfig>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AppDBConfig _item_1;
        _item_1 = new AppDBConfig();
        if (_cursor.isNull(_cursorIndexOfConfigKey)) {
          _item_1.configKey = null;
        } else {
          _item_1.configKey = _cursor.getString(_cursorIndexOfConfigKey);
        }
        if (_cursor.isNull(_cursorIndexOfConfigValue)) {
          _item_1.configValue = null;
        } else {
          _item_1.configValue = _cursor.getString(_cursorIndexOfConfigValue);
        }
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
