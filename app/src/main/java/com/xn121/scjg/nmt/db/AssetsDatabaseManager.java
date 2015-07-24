package com.xn121.scjg.nmt.db;
  
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.xn121.scjg.nmt.util.Log;

public class AssetsDatabaseManager {  
    private static String databasepath = "/data/data/%s/database"; // %s is packageName  
      
      
    // A mapping from assets database file to SQLiteDatabase object  
    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();  
      
    private Context context = null;  
      
    // Singleton Pattern  
    private static AssetsDatabaseManager mInstance = null;  
      
    /** 
     * Initialize AssetsDatabaseManager 
     * @param context, context of application 
     */  
    public static void initManager(Context context){  
        if(mInstance == null){  
            mInstance = new AssetsDatabaseManager(context);  
        }  
    }  
      
    /** 
     * Get a AssetsDatabaseManager object 
     * @return, if success return a AssetsDatabaseManager object, else return null 
     */  
    public static AssetsDatabaseManager getManager(){  
        return mInstance;  
    }  
      
    private AssetsDatabaseManager(Context context){  
        this.context = context;  
    }  
      
    /** 
     * Get a assets database, if this database is opened this method is only return a copy of the opened database 
     * @param dbfile, the assets file which will be opened for a database 
     * @return, if success it return a SQLiteDatabase object else return null 
     */  
    public SQLiteDatabase getDatabase(String dbfile) {  
        if(databases.get(dbfile) != null){
        	Log.createDBLog( String.format("Return a database copy of %s", dbfile));
            return (SQLiteDatabase) databases.get(dbfile);  
        }  
        if(context==null)  
            return null;  
          
        String sfile = getDatabaseFile(dbfile);  
          
        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);  
        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid  
        File file = new File(sfile);  
        if(!flag || !file.exists()){
        	Log.createDBLog( String.format("Create database %s", dbfile));
        	String spath = getDatabaseFilepath();
            file = new File(spath);  
            if(!file.exists() && !file.mkdirs()){  
            	Log.createDBLog( "Create \""+spath+"\" fail!");  
                return null;  
            }  
            if(!copyAssetsToFilesystem(dbfile, sfile)){  
            	Log.createDBLog( String.format("Copy %s to %s fail!", dbfile, sfile));  
                return null;  
            }  
              
            dbs.edit().putBoolean(dbfile, true).commit();  
        }else{
            Log.createDBLog( String.format("database %s exist flag= %s file=%s", dbfile, flag, file.exists()));
        }
          
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);  
        if(db != null){  
            databases.put(dbfile, db);  
        }  
        return db;  
    }  
      
    private String getDatabaseFilepath(){  
        return String.format(databasepath, context.getApplicationInfo().packageName);  
    }  
      
    private String getDatabaseFile(String dbfile){  
        return getDatabaseFilepath()+"/"+dbfile;  
    }  
      
    /**
     * 保存Assets中.mp3格式的数据库到/data/data/%s/database下，并更改后缀为.db
     * 由于Android的Assets文件夹中的单个文件大小超过1M会被压缩，导致读取异常，但改为mp3格式不会被压缩。
     * @param assetsSrc 数据库名称，例如：weather_index.db
     * @param des 数据库保存全路径，例如：/data/data/%s/database/weather_index.db
     * @return
     */
    private boolean copyAssetsToFilesystem(String assetsSrc, String des){
    	Log.createDBLog("Copy " + assetsSrc + " to " + des);
        InputStream istream = null;
        OutputStream ostream = null;
        try{  
            AssetManager am = context.getAssets();
            String dbName = assetsSrc;
            istream = am.open(dbName);  
            ostream = new FileOutputStream(des);  
            byte[] buffer = new byte[1024];  
            int length;  
            while ((length = istream.read(buffer))>0){  
                ostream.write(buffer, 0, length);  
            }  
            istream.close();  
            ostream.close();  
        }  
        catch(Exception e){  
            try{  
                if(istream!=null)  
                    istream.close();  
                if(ostream!=null)  
                    ostream.close();  
            }  
            catch(Exception ee){  
                ee.printStackTrace();  
            }  
            return false;  
        }  
        return true;  
    }  
      
    /** 
     * Close assets database 
     * @param dbfile, the assets file which will be closed soon 
     * @return, the status of this operating 
     */  
    public boolean closeDatabase(String dbfile){  
        if(databases.get(dbfile) != null){  
            SQLiteDatabase db = (SQLiteDatabase) databases.get(dbfile);  
            db.close();  
            databases.remove(dbfile);  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * Close all assets database 
     */  
    public static void closeAllDatabase(){  
        if(mInstance != null){  
            for(int i=0; i<mInstance.databases.size(); ++i){  
                if(mInstance.databases.get(i)!=null){  
                    mInstance.databases.get(i).close();  
                }  
            }  
            mInstance.databases.clear();  
        }  
    }  
}  