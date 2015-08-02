package com.xn121.scjg.nmt.fragement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xn121.scjg.nmt.IntroActivity;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.netInterface.NetUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 7/16/15.
 */
public class AboutFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private RequestQueue queue;
    private RetryPolicy retryPolicy;
    private TextView introduction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_aboutus, null);
            init();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void init(){
        introduction = (TextView)rootView.findViewById(R.id.introduction);
        introduction.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        queue = Volley.newRequestQueue(activity);
        retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", "接口异常");
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.introduction:
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                startActivity(intent);
                break;
        }
    }
}
