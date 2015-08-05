package com.xn121.scjg.nmt.scaleview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SystemUtil {

	public static String ForMatterTime(String mTime) {
		return null;
	}

	/*
	 * get version code of some package
	 * 
	 * @param ctx context packageName the package name, for example,
	 * "com.letv.tv"
	 * 
	 * @return version code
	 */
	public static String getPackageName(Context ctx) {
		if (ctx != null) {
			return ctx.getPackageName();
		}

		return "";
	}

	/*
	 * 获取版本号
	 * 
	 * @return version code
	 */
	public static int getVersionCode(Context ctx) {
		try {
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/*
	 * 获取版本名称
	 * 
	 * @return version name
	 */
	public static String getVersionName(Context ctx) {
		try {
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return packInfo.versionName;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/*
	 * get version code of some package
	 * 
	 * @param ctx context packageName the package name, for example,
	 * "com.letv.tv"
	 * 
	 * @return version code
	 */
	public static int getVersionCode(Context ctx, final String packageName) {
		try {
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(
					packageName, 0);
			return packInfo.versionCode;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/*
	 * get version name of some package
	 * 
	 * @param ctx context packageName the package name, for example,
	 * "com.letv.tv"
	 * 
	 * @return version name
	 */
	public static String getVersionName(Context ctx, final String packageName) {
		try {
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(
					packageName, 0);
			return packInfo.versionName;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * 获取操作系统版本4.0.4
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取SDK version
	 * 
	 * @return SDK version
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (NumberFormatException e) {
			version = 0;
		}
		return version;
	}

	/*
	 * 当前网络信息
	 */
	public static NetworkInfo getAvailableNetWorkInfo(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.isAvailable()) {
			return activeNetInfo;
		} else {
			return null;
		}
	}

	/*
	 * 当前网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null) {
			netSataus = info.isAvailable() || info.isConnected();
		}
		return netSataus;
	}
}
