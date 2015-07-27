package com.xn121.scjg.nmt.netInterface;

import android.content.Context;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by admin on 7/17/15.
 */
public class NetUtil {


    public static final String GETPRICE = "http://webapi.weather.com.cn/data/?areaid=%s&type=%s&date=%s&appid=%s";
    public static final String GETPRICEOFDOMAIN = "http://61.4.185.10:8080/rsvp/cn-vertical/answer/%s?lat=%s&lon=%s&domains=product";
    public static final String GETPROFITSTATEMENT = "http://scjg.xn121.com/nmt/apifornmt2.php?type=%s&corpname=%s&startpoint=%s&endprov=%s&price=%s&number=%s&vehicletype=%s&fueltype=%s&othercosts=%s";
    public static final String APPID = "977d7b917beb423f";

    private static final String PRIVATEKEY = "nongmaotong_webapi_data";



    public static String getSignature(String data){
        String str = null;
        try {
            byte[] keyBytes = PRIVATEKEY.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            str = Base64Encoder.encode(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }






}
