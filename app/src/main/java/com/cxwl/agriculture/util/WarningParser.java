package com.cxwl.agriculture.util;

import android.util.JsonToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;

/**
 * Created by admin on 15/8/14.
 */
public class WarningParser {

    private static final String warningArray = "[" +
            "{'冰雹':'bb'}," +
            "{'暴雪':'bx'}," +
            "{'暴雨':'by'}," +
            "{'大风':'df'}," +
            "{'道路结冰':'dljb'}," +
            "{'大雾':'dw'}," +
            "{'干旱':'gh'}," +
            "{'高温':'gw'}," +
            "{'寒潮':'hc'}," +
            "{'空间天气灾害':'kjtq'}," +
            "{'雷电':'ld'}," +
            "{'霾':'m'}," +
            "{'沙尘暴':'scb'}," +
            "{'霜冻':'sd'}," +
            "{'台风':'tf'}]";

    private static final String colorArray = "[" +
            "{'蓝':'b'}," +
            "{'橙':'o'}," +
            "{'红':'r'}," +
            "{'黄':'y'}]";

    public static String getWeatherKey(String title){
        return getKey(title, warningArray);
    }

    public static String getColorKey(String title){
        return getKey(title, colorArray);
    }

    public static String getKey(String title, String array){
        String str = null;
        if(title == null){
            return str;
        }
        try {
            JSONArray wa= new JSONArray(array);

            for(int i=0; i < wa.length(); i++){
                JSONObject object = wa.getJSONObject(i);
                Iterator iterator = object.keys();
                while (iterator.hasNext()){
                    String key = iterator.next().toString();
                    if(title.contains(key)){
                        str = object.getString(key);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }
}
