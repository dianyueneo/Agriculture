package com.xn121.scjg.nmt.fragement;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.netInterface.NetUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 7/16/15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private TextView tv;
    private Button btn;
    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_temp, null);
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        tv = (TextView)rootView.findViewById(R.id.mtv);
        btn = (Button)rootView.findViewById(R.id.mbtn);
        btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        queue = Volley.newRequestQueue(activity);
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

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("test", response.toString());
//                tv.setText(response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("test", "接口异常");
//                tv.setText("接口异常");
//            }
//        });


        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        queue.add(jsonObjectRequest);

    }


    private void getMarketNameList(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121list";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0, 6) + "&key=" + key);

        Log.i("test", url);

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                tv.setText(response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tv.setText("接口异常");
//            }
//        });

        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        queue.add(jsonObjectRequest);

    }

    private void getProductNameList(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121list";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                tv.setText(response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tv.setText("接口异常");
//            }
//        });

        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        queue.add(jsonObjectRequest);

    }


    private void getPriceofDomain(String areaid){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "forecast";
        String publicKey = String.format(NetUtil.GETPRICEOFDOMAIN, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);

        Log.i("test", url);

        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        queue.add(jsonObjectRequest);

    }


    private void getProfitStatement(String type, String corpname, String startpoint, String endprov, String price, String number, String vehicletype, String fueltype, String othercosts){

        String url = String.format(NetUtil.GETPROFITSTATEMENT, type, corpname, startpoint, endprov, price, number, vehicletype, fueltype, othercosts);
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

        queue.add(jsonObjectRequest);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mbtn:
//                getPrice("10101ganguo_hetaomarket1032013m7");
//                getTradeLeads();
//                getMarketNameList("market_10101");
//                getProductNameList("product_chaye");
//                getObserve("101010100");
//                getForecast("101010100");
                getProfitStatement("acquisition", "bailuobo", "50", "10126", "1.8", "1000", "1", "1", "300");
                break;
            default:
                break;
        }

    }
}
