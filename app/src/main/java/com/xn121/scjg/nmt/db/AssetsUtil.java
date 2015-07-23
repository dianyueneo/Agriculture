package com.xn121.scjg.nmt.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xn121.scjg.nmt.bean.Market;
import com.xn121.scjg.nmt.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15/7/23.
 */
public class AssetsUtil {
    private static final String DB_DEFAULT = "cityinfo.db";
    private static final String SQL_CITY = "select * from province";
    private static final String SQL_Market = "select * from market where provinceId=?";

    public static void initDb(Context context){
        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager manager = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = manager.getDatabase(DB_DEFAULT);
        db.close();
        manager.closeDatabase(DB_DEFAULT);
    }

    public static List<Province> getProvinceList(Context context){

        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager manager = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = manager.getDatabase(DB_DEFAULT);

        Cursor cursor = null;
        List<Province> list = new ArrayList<Province>();
        Province province = null;
        cursor = db.rawQuery(SQL_CITY, null);
        int provinceName_index = cursor.getColumnIndex("provinceName");
        int provinceId_index = cursor.getColumnIndex("provinceId");


        while (cursor.moveToNext()){
            province = new Province();
            province.setProvinceName(cursor.getString(provinceName_index));
            province.setProvinceId(cursor.getString(provinceId_index));
            list.add(province);
        }

        cursor.close();
        db.close();
        manager.closeDatabase(DB_DEFAULT);

        return list;
    }


    public static List<Market> getMarketList(Context context, String provinceId){

        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager manager = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = manager.getDatabase(DB_DEFAULT);

        Cursor cursor = null;
        List<Market> list = new ArrayList<Market>();
        Market market = null;
        cursor = db.rawQuery(SQL_Market, new String[]{provinceId});
        int marketName_index = cursor.getColumnIndex("marketName");
        int marketId_index = cursor.getColumnIndex("marketId");

        while (cursor.moveToNext()){
            market = new Market();
            market.setMarketId(cursor.getString(marketId_index));
            market.setMarketName(cursor.getString(marketName_index));
            list.add(market);
        }

        cursor.close();
        db.close();
        manager.closeDatabase(DB_DEFAULT);

        return list;
    }

}
