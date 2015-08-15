package com.cxwl.agriculture.util;

import android.content.Context;

import com.cxwl.agriculture.R;

import org.json.JSONObject;

/**
 * Created by admin on 15/8/14.
 */
public class WarningParser {

    public static String getIcon(JSONObject warning, Context context){
        String w4 = warning.optString("w4");
        String w6 = warning.optString("w6");

        String weatherkey = WarningParser.getWarningKey(w4);
        String colorkey = WarningParser.getColorKey(w6);
        if(weatherkey == null || colorkey == null){
            return null;
        }
        return "hf_warning_"+ weatherkey +"_"+ colorkey;
    }

    public static String getWarningKey(String w4){
        String value = null;
        int key = Integer.parseInt(w4);
        switch (key){
            case 1:
                value = "tf";
                break;
            case 2:
                value = "by";
                break;
            case 3:
                value = "bx";
                break;
            case 4:
                value = "hc";
                break;
            case 5:
                value = "df";
                break;
            case 6:
                value = "scb";
                break;
            case 7:
                value = "gw";
                break;
            case 8:
                value = "gh";
                break;
            case 9:
                value = "ld";
                break;
            case 10:
                value = "bb";
                break;
            case 11:
                value = "sd";
                break;
            case 12:
                value = "dw";
                break;
            case 13:
                value = "m";
                break;
            case 14:
                value = "dljb";
                break;
            case 15:
                value ="kjtq";
                break;
            default:
                break;
        }
        return value;
    }

    public static String getColorKey(String w6){
        String value = null;
        int key = Integer.parseInt(w6);
        switch (key){
            case 1:
                value = "b";
                break;
            case 2:
                value = "y";
                break;
            case 3:
                value = "o";
                break;
            case 4:
                value = "r";
                break;
            default:
                break;
        }
        return value;
    }

}
