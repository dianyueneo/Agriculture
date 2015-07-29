package com.xn121.scjg.nmt.fragement;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.adapter.AskPriceListAdapter;
import com.xn121.scjg.nmt.bean.Price;
import com.xn121.scjg.nmt.bean.Product;
import com.xn121.scjg.nmt.netInterface.NetUtil;
import com.xn121.scjg.nmt.util.JsonParser;
import com.xn121.scjg.nmt.volley.XMLRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 7/16/15.
 */
public class AskPriceFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private View rootView;
    private RequestQueue queue;
    private RetryPolicy retryPolicy;

    private ImageView speech, clear;

    private ListView listView;
    private AskPriceListAdapter adapter;
    private List<Product> productList;

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

        listView = (ListView)rootView.findViewById(R.id.listview);
        adapter = new AskPriceListAdapter(getActivity());
        productList = new ArrayList<Product>();
        adapter.setList(productList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                clearProduct();
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
                Log.i("test", recognizerResult.toString());
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
                Product product = parseXml(response);
                if(product != null){
                    addProduct(product);
                }else{
                    Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
                }
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

    private Product parseXml(XmlPullParser response){
        Product product = null;
        try {
            int eventType = response.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String nodeName = response.getName();
                        if ("errorcode".equals(nodeName)) {
                            String errorcode = response.nextText();
                            if("0".equals(errorcode)){
                                product = new Product();
                            }else{
                                break;
                            }
                        }else if("rawprovince".equals(nodeName)){
                            String province = response.nextText();
                            product.setProvince(province);
                        }else if("rawproduct".equals(nodeName)){
                            String pro = response.nextText();
                            product.setProduct(pro);
                        }else if("dates".equals(nodeName)){
                            String date = response.nextText();
                            product.setDate(date);
                        }else if("code".equals(nodeName)){
                            String code = response.nextText();
                            product.setCode(Integer.parseInt(code));
                        }else if("market".equals(nodeName)){
                            String pro = response.getAttributeValue(0);
                            String province = response.getAttributeValue(1);
                            String name = response.getAttributeValue(2);
                            String address = response.getAttributeValue(3);
                            String lat = response.getAttributeValue(4);
                            String lon = response.getAttributeValue(5);
                            String date = response.getAttributeValue(6);
                            String pri = response.nextText();

                            Price price = new Price();
                            price.setProduct(pro);
                            price.setProvince(province);
                            price.setName(name);
                            price.setAddress(address);
                            price.setLat(Double.parseDouble(lat));
                            price.setLon(Double.parseDouble(lon));
                            price.setDate(date);
                            price.setPrice(pri);

                            if(product.getList() == null){
                                List<Price> list = new ArrayList<Price>();
                                list.add(price);
                                product.setList(list);
                            }else{
                                product.getList().add(price);
                            }

                        }else if("RRP".equals(nodeName)){
                            String RRP = response.nextText();
                            product.setRrp(RRP);
                        }else if("AP".equals(nodeName)){
                            String AP = response.nextText();
                            product.setAp(AP);
                        }
                        break;
                }
                eventType = response.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return product;
        }
    }

    private void addProduct(Product product){
        productList.add(product);
        adapter.setList(productList);
    }

    private void clearProduct(){
        productList.clear();;
        adapter.setList(productList);
    }

}
