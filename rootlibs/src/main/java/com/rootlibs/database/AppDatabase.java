package com.rootlibs.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {AppDBConfig.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppConfigControl getControl();
    private static Context context=null;
    public static void setContext(Context ct){
        context=ct;
    }
    private static AppDatabase INSTANCE;
    public static AppDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, "app_config")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     * <p>
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new SelectAllAsync(selectAll).execute();
        }
    };
    private static InterDataBase selectAll = new InterDataBase<List<AppDBConfig>>() {
        @Override
        public void exeResult(List<AppDBConfig> result) {
            mAllConfig.clear();
            mAllConfig.addAll(result);
        }
    };

    private static List<AppDBConfig> mAllConfig = new ArrayList<>();

    public boolean hasKey(String key) {
        for (AppDBConfig config : mAllConfig) {
            if (config.configKey.equals(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class SelectAllAsync extends AsyncTask<Void, Void, List<AppDBConfig>> {
        private final AppConfigControl mDao;
        private List<AppDBConfig> mAllConfig = new ArrayList<>();
        private InterDataBase listener;

        SelectAllAsync(InterDataBase listener) {
            mDao = getDatabase().getControl();
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(List<AppDBConfig> appConfigs) {
            super.onPostExecute(appConfigs);
            mAllConfig.addAll(appConfigs);
            if (listener != null) {
                listener.exeResult(mAllConfig);
            }
        }

        @Override
        protected List<AppDBConfig> doInBackground(final Void... params) {
            return mDao.selectAll();
        }
    }


    public void insert(AppDBConfig word, InterDataBase listener) {
        new insertAsyncTask(listener).execute(word);
    }

    public void update(AppDBConfig word, InterDataBase listener) {
        new upDataAsyncTask(listener).execute(word);
    }

    public void delete(AppDBConfig word, InterDataBase listener) {
        new deleteAsyncTask(listener).execute(word);
    }

    public void selectAll(InterDataBase listener) {
        new SelectAllAsync(listener).execute();
    }

    public void selectKey(String key, InterDataBase listener) {
        new SelectAsync(listener).execute(key);
    }

    private static class insertAsyncTask extends AsyncTask<AppDBConfig, Void, Void> {
        private AppConfigControl mAsyncTaskDao;
        private InterDataBase listener;

        insertAsyncTask(InterDataBase listener) {
            this.listener = listener;
            mAsyncTaskDao = getDatabase().getControl();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            执行前
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            执行完成
            if (listener != null) {
                listener.exeResult("");
            }
        }

        @Override
        protected Void doInBackground(final AppDBConfig... params) {
            mAsyncTaskDao.insert(params[0]);
            mAllConfig.add(params[0]);//顺便放到缓存里面去、用于判断是更新还是插入
//            mAsyncTaskDao.updateWord(1,"Changed"+params[0].getWord());
            return null;
        }
    }


    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class SelectAsync extends AsyncTask<String, Void, List<AppDBConfig>> {
        private final AppConfigControl mDao;
        private List<AppDBConfig> mAllConfig = new ArrayList<>();
        private InterDataBase listener;

        SelectAsync(InterDataBase listener) {
            mDao = getDatabase().getControl();
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(List<AppDBConfig> appConfigs) {
            super.onPostExecute(appConfigs);
            mAllConfig.addAll(appConfigs);
            if (listener != null) {
                listener.exeResult(mAllConfig);
            }
        }

        @Override
        protected List<AppDBConfig> doInBackground(final String... params) {
            return mDao.queryByKey(params);
        }
    }


    private static class upDataAsyncTask extends AsyncTask<AppDBConfig, Void, Void> {
        private AppConfigControl mAsyncTaskDao;
        private InterDataBase listener;

        upDataAsyncTask(InterDataBase listener) {
            this.listener = listener;
            mAsyncTaskDao = getDatabase().getControl();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            执行前
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            执行完成
            if (listener != null) {
                listener.exeResult("");
            }
        }

        @Override
        protected Void doInBackground(final AppDBConfig... params) {
            mAsyncTaskDao.updata(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<AppDBConfig, Void, Void> {
        private AppConfigControl mAsyncTaskDao;
        private InterDataBase listener;

        deleteAsyncTask(InterDataBase listener) {
            this.listener = listener;
            mAsyncTaskDao = getDatabase().getControl();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            执行前
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            执行完成
            if (listener != null) {
                listener.exeResult("");
            }
        }

        @Override
        protected Void doInBackground(final AppDBConfig... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
