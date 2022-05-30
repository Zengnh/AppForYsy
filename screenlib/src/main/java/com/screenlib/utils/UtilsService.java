package com.screenlib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class UtilsService {

	public static boolean isServiceWork(Context mContext, String className) {
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			RunningServiceInfo serviceInfo = serviceList.get(i);
			ComponentName serviceName = serviceInfo.service;
			if (serviceName.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}


	public static void setWindowStatusBarColor(Activity activity, int colorResId) {
			try {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
