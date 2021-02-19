package com.rootlibs.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

/***
 * 亲测能用
 *
 *    def room_version = "2.2.4"
 *     //room
 *     implementation "androidx.room:room-runtime:$room_version"
 *     annotationProcessor "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor
 *     kapt 'androidx.room:room-compiler:2.2.4'
 *     // optional - Kotlin Extensions and Coroutines support for Room
 *     implementation "androidx.room:room-ktx:$room_version"
 *     // optional - RxJava support for Room
 *     implementation "androidx.room:room-rxjava2:$room_version"
 *     // optional - Guava support for Room, including Optional and ListenableFuture
 *     implementation "androidx.room:room-guava:$room_version"
 *     // Test helpers
 *     testImplementation "androidx.room:room-testing:$room_version"
 *
 *
 *    // build.gradle 记得加kapt（）注释。不然使用不了
 *    apply plugin: 'kotlin-kapt'
 */
public class ToolGoogleRoom {
    public void test(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "root_libs").build();
                AppConfigControl userDao = db.getControl();
//                AppDBConfig user = new AppDBConfig();
//                user.configKey = "ssss";
//                user.configValue = "kankan";
//                userDao.insertAll(user);
                List<AppDBConfig> liset = userDao.selectAll();
                List<AppDBConfig> bean = userDao.queryByKey(new String[]{"ssss"});
                Log.i("znh_db", liset.size() + "  @@  " + bean.size());
                if (liset.size() > 0) {
                    Log.i("znh_db", liset.get(0).configKey + "    " + liset.get(0).configValue);
                }
            }
        }).start();
    }

    private static ToolGoogleRoom instance;

    public static ToolGoogleRoom getInstance() {
        if (instance == null) {
            synchronized (ToolGoogleRoom.class) {
                if (instance == null) {
                    instance = new ToolGoogleRoom();
                }
            }
        }
        return instance;
    }

    private AppConfigControl userDao;

    public void initContent(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "root_libs").build();
        userDao = db.getControl();

    }

    public void initAllInfo() {
        allConfigInfo = userDao.selectAll();
    }

    private List<AppDBConfig> allConfigInfo = new ArrayList<>();

    public List<AppDBConfig> getAllConfig() {
        return allConfigInfo;
    }

    public List<AppDBConfig> searchKey(String key) {
        List<AppDBConfig> bean = userDao.queryByKey(new String[]{key});
        return bean;
    }

    public void saveValue(String key, String value) {
        AppDBConfig config = new AppDBConfig();
        config.configKey = key;
        config.configValue = value;
        List<AppDBConfig> bean = searchKey(key);
        if (bean.size() > 0) {
//            更新
            userDao.updata(config);
            for (int i = 0; i < allConfigInfo.size(); i++) {
                if (allConfigInfo.get(i).configKey.equals(key)) {
                    allConfigInfo.get(i).configValue = value;
                }
            }
        } else {
//            保存
            userDao.insert(config);
            AppDBConfig save = new AppDBConfig();
            save.configKey = key;
            save.configValue = value;
            allConfigInfo.add(save);
        }

    }
}
