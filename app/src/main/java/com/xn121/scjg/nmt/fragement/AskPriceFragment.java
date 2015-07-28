package com.xn121.scjg.nmt.fragement;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.speech.RecognizerResult;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.netInterface.NetUtil;
import com.xn121.scjg.nmt.util.JsonParser;
import com.xn121.scjg.nmt.volley.XMLRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by admin on 7/16/15.
 */
public class AskPriceFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private View rootView;
    private RequestQueue queue;
    private RetryPolicy retryPolicy;

    private ImageView speech, clear;

    private String appId = "55b4a6ff";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_ask, null);
            initView();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        queue = Volley.newRequestQueue(activity);
        retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    private void initView(){
        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=" + appId);
        speech = (ImageView)rootView.findViewById(R.id.speech);
        clear = (ImageView)rootView.findViewById(R.id.clear);

        speech.setOnClickListener(this);
        speech.setOnLongClickListener(this);
        clear.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:

                break;
            default:
                break;
        }

    }

    private void startSpeech(){
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(getActivity(), null);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        mIat.startListening(mRecognizerListener);
    }

    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(com.iflytek.cloud.RecognizerResult recognizerResult, boolean b) {
            if(!b){
                getPriceofDomain(parseResult(recognizerResult));
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    @Override
    public boolean onLongClick(View view) {
        boolean deal = false;

        switch (view.getId()){
            case R.id.speech:
                startSpeech();
                deal = true;
                break;
            default:
                break;
        }

        return deal;
    }

    private String parseResult(com.iflytek.cloud.RecognizerResult results) {
        return JsonParser.parseIatResult(results.getResultString());
    }

    private void getPriceofDomain(String question){
        String str = null;
        try {
            str = URLEncoder.encode(question, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(NetUtil.GETPRICEOFDOMAIN, str);

        Log.i("test", url);

        XMLRequest xmlRequest = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                parseXml(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
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

}
