package com.xn121.scjg.nmt.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xn121.scjg.nmt.bean.Goods;
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
    private static final String SQL_MARKET = "select * from market where provinceId=?";
    private static final String SQL_GOODS = "select * from goods where category=?";

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

        while (cursor.moveToNext()){
            province = new Province();
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            province.setProvinceId(cursor.getString(cursor.getColumnIndex("provinceId")));
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
        cursor = db.rawQuery(SQL_MARKET, new String[]{provinceId});

        while (cursor.moveToNext()){
            market = new Market();
            market.setMarketId(cursor.getString(cursor.getColumnIndex("marketId")));
            market.setMarketName(cursor.getString(cursor.getColumnIndex("marketName")));
            list.add(market);
        }

        cursor.close();
        db.close();
        manager.closeDatabase(DB_DEFAULT);

        return list;
    }


    public static List<Goods> getGoodsList(Context context, String category){

        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager manager = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = manager.getDatabase(DB_DEFAULT);

        Cursor cursor = db.rawQuery(SQL_GOODS, new String[]{category});
        Goods goods = null;
        List<Goods> list = new ArrayList<Goods>();

        while (cursor.moveToNext()){
            goods = new Goods();
            goods.setPinyin(cursor.getString(cursor.getColumnIndex("pinyin")));
            goods.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(goods);
        }

        cursor.close();
        db.close();
        manager.closeDatabase(DB_DEFAULT);

        return list;
    }

}
