package com.rootlibs.service_root;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class GetSmsService extends IntentService {
    private AlarmManager alarmManager = null;
    private PendingIntent alarmIntent = null;
    
    public GetSmsService(){
        super("WtacService");
    }
    
    public GetSmsService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        String ALARM_ACTION = "com.ysy.receiver.ACTION_WTAC_ALAEM";
        Intent intentTo = new Intent(ALARM_ACTION);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intentTo, 0);
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        final Context context = this.getApplicationContext();
        
        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long triggerAtMillis = SystemClock.elapsedRealtime()+(5*1000);
        long intervalMillis = 5*1000;    //间隔时间
        alarmManager.setInexactRepeating(alarmType, triggerAtMillis, intervalMillis, alarmIntent);
//        if(PhoneInfo.isConnectInternet(context)){
//            System.out.println("从服务器获取数据："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            try {
//                Thread.sleep(2*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("进行短信发送："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        }
    }

}