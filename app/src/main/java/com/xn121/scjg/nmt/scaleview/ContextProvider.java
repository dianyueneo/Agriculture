package com.xn121.scjg.nmt.scaleview;

import android.content.Context;

public final class ContextProvider {

	private static Context sContext = null;

	/**
	 * This function should be invoked in Application while the application is
	 * been created.
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		if (context == null) {
			throw new NullPointerException(
					"Can not use null initlialized application context");
		}
		sContext = context;
	}

	/**
	 * Get application context.
	 * 
	 * @return
	 */
	public static Context getApplicationContext() {
		if (sContext == null) {
			throw new NullPointerException("Global application uninitialized");
		}
		return sContext;
	}

	private ContextProvider() {
	}
}
