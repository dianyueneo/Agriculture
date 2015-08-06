package com.cxwl.agriculture.util;


/**
 * 此类不对外开放，测试Log,可不添加
 */
public class Log {
	private static final boolean LOG = false;

	//tag
	private static final String Tag_AOI = "TEST-AOI";
	private static final String Tag_METADATA = "TEST-METADATA";
	private static final String Tag_TRAFFIC = "TEST-TRAFFIC";
	private static final String Tag_APPSTATUS = "TEST-APPSTATUS";
	private static final String Tag_DB = "TEST-DB";
	private static final String Tag_PN = "TEST-PN";
	
	private static final int LOG_BUFFER = 3*1024;

	public static void createLogI(String tag, String msg) {
		createLog(tag, msg);
	}

	public static void createAOILog(String msg) {
		createLog(Tag_AOI, msg);
	}
	
	public static void createMETADATALog(String msg) {
		createLog(Tag_METADATA, msg);
	}
	
	public static void createTRAFFICLog(String msg) {
		createLog(Tag_TRAFFIC, msg);
	}
	
	public static void createAPPSTATUSLog(String msg) {
		createLog(Tag_APPSTATUS, msg);
	}
	
	public static void createDBLog(String msg){
		createLog(Tag_DB, msg);
	}
	
	public static void ceratePNLog(String msg){
		createLog(Tag_PN, msg);
	}
	
	
	public static void createLogException(String tag, Exception exception) {
		if (LOG) {
			android.util.Log.i(tag, exception.toString());
		}
	}
	
	private static void createLog(String tag, String msg){
		if (LOG) {
			if(msg.length() > LOG_BUFFER){
				String[] strToStrA = StrToStrA(msg, LOG_BUFFER);
				for (String str : strToStrA) {
					android.util.Log.i(tag, str);
				}
			}else{
				android.util.Log.i(tag, msg);
			}
		}
	}
	
	
	private static String[] StrToStrA(String str, int len) {
		String[] tmp = new String[(str.length() + len - 1) / len];
		for (int i = 0; i < tmp.length; i++) {
			if (i == tmp.length - 1) {
				tmp[i] = str.substring(i * len, str.length());
			} else {
				tmp[i] = str.substring(i * len, (i+1)*len);
			}
		}
		return tmp;
	}
	
}
