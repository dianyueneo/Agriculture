package com.xn121.scjg.nmt.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xn121.scjg.nmt.MapActivity;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.netInterface.NetUtil;
import com.xn121.scjg.nmt.volley.XMLRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 7/16/15.
 */
public class AboutFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private TextView tv;
    private Button btn;
    private RequestQueue queue;
    private RetryPolicy retryPolicy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_temp2, null);
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }
        return rootView;
    }


    private void getPrice(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }


    private void getTradeLeads(){
        String areaid = "chinagqxx";
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121gqxx";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("test", response.toString());
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", "接口异常");
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }


    private void getMarketNameList(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121list";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0, 6) + "&key=" + key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }

    private void getProductNameList(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121list";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }


    private void getObserve(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "observe";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }


    private void getForecast(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "forecast";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }


    private void getPriceofDomain(String question, String lat, String lon){
        String str = null;
        try {
            str = URLEncoder.encode(question, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(NetUtil.GETPRICEOFDOMAIN, str, lat, lon);

        Log.i("test", url);

        XMLRequest xmlRequest = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                parseXml(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", "接口异常");
                tv.setText("接口异常");
            }
        });

        xmlRequest.setRetryPolicy(retryPolicy);
        queue.add(xmlRequest);

    }

    private void parseXml(XmlPullParser response){
        try {
            int eventType = response.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String nodeName = response.getName();
                        if ("city".equals(nodeName)) {
                            String pName = response.getAttributeValue(0);
                            Log.d("TAG", "pName is " + pName);
                        }
                        break;
                }
                eventType = response.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mbtn:
//                getPrice("10101ganguo_hetaomarket1032015m7");
//                getTradeLeads();
//                getMarketNameList("market_10101");
//                getProductNameList("product_chaye");
//                getObserve("101010100");
//                getForecast("101010100");
                getPriceofDomain("北京白菜价格","39.911421", "116.460934");
//                getProfitStatement("sales", "bailuobo", "50", "10126", "1.8", "1000", "1", "1", "300");
//                showInfo();
//                openMap();
                break;
            default:
                break;
        }

    }

    private void openMap(){
        Intent intent = new Intent(this.getActivity(), MapActivity.class);
        startActivity(intent);
    }

    private void showInfo(){
        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getActivity().getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;
        Log.i("test", "screenWidth:"+screenWidth+" screenHeight:"+screenHeight+" density:"+density);
        tv.setText("screenWidth:" + screenWidth + " screenHeight:" + screenHeight + " density:" + density);
    }

    private void getProfitStatement(String type, String corpname, String startpoint, String endprov, String price, String number, String vehicletype, String fueltype, String othercosts){

        String url = String.format(NetUtil.GETPROFITSTATEMENT, type, corpname, startpoint, endprov, price, number, vehicletype, fueltype, othercosts);
        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("test", response.toString());
                parseProfitStatement(response);
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", "接口异常");
                tv.setText("接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }

    private void parseProfitStatement(JSONObject jsonObject){
        boolean success = false;
        try {
            String status = jsonObject.getString("status");
            if("success".equals(status)){
                JSONObject startpoint = jsonObject.getJSONObject("startpoint");
                Double lon = Double.parseDouble(startpoint.getString("lon"));
                Double lat = Double.parseDouble(startpoint.getString("lat"));

                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("start_lon", lon);
                intent.putExtra("start_lat", lat);
                intent.putExtra("end", "上海");
                startActivity(intent);
                success = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(!success){
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT);
            }
        }
    }
}
