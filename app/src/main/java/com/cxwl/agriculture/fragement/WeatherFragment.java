package com.cxwl.agriculture.fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.cxwl.agriculture.MyApplication;
import com.cxwl.agriculture.R;
import com.cxwl.agriculture.WarningActivity;
import com.cxwl.agriculture.chart.ChartView;
import com.cxwl.agriculture.chart.LineData;
import com.cxwl.agriculture.util.NotificationUtil;
import com.cxwl.agriculture.util.WarningParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import cn.com.weather.api.WeatherAPI;
import cn.com.weather.beans.Weather;
import cn.com.weather.constants.Constants;
import cn.com.weather.listener.AsyncResponseHandler;

/**
 * Created by admin on 7/16/15.
 */
public class WeatherFragment extends Fragment implements AMapLocationListener{

    private View rootView;

    private ChartView chartView;

    private TextView city, date, week, temperature, tv_weather, wend;
    private ImageView img_weather_1, img_weather_2, img_weather_3, img_weather_4, img_weather_5, img_weather_6, img_weather_7;
    private TextView text_weather_3, text_weather_4, text_weather_5, text_weather_6, text_weather_7;
    private ImageView warningImage;

    private LocationManagerProxy mLocationManagerProxy;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("test", "onCreateView======"+this.getClass().getSimpleName());
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_weather, null);
            init();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void init(){
        city = (TextView)rootView.findViewById(R.id.city);
        date = (TextView)rootView.findViewById(R.id.date);
        week = (TextView)rootView.findViewById(R.id.week);
        temperature = (TextView)rootView.findViewById(R.id.temperature);
        tv_weather = (TextView)rootView.findViewById(R.id.weather);
        wend = (TextView)rootView.findViewById(R.id.wend);

        img_weather_1 = (ImageView)rootView.findViewById(R.id.img_weather_1);
        img_weather_2 = (ImageView)rootView.findViewById(R.id.img_weather_2);
        img_weather_3 = (ImageView)rootView.findViewById(R.id.img_weather_3);
        img_weather_4 = (ImageView)rootView.findViewById(R.id.img_weather_4);
        img_weather_5 = (ImageView)rootView.findViewById(R.id.img_weather_5);
        img_weather_6 = (ImageView)rootView.findViewById(R.id.img_weather_6);
        img_weather_7 = (ImageView)rootView.findViewById(R.id.img_weather_7);

        warningImage = (ImageView)rootView.findViewById(R.id.warning);

        text_weather_3 = (TextView)rootView.findViewById(R.id.text_weather_3);
        text_weather_4 = (TextView)rootView.findViewById(R.id.text_weather_4);
        text_weather_5 = (TextView)rootView.findViewById(R.id.text_weather_5);
        text_weather_6 = (TextView)rootView.findViewById(R.id.text_weather_6);
        text_weather_7 = (TextView)rootView.findViewById(R.id.text_weather_7);

        chartView = (ChartView)rootView.findViewById(R.id.chart);
        chartView.chartViewType = ChartView.ChartViewType.ChartViewLine;

        mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
        mLocationManagerProxy.setGpsEnable(false);

        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK)-1;

        date.setText(month + "月" + day + "日");
        String[] weekdatyname = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        week.setText(weekdatyname[weekday]);

        startLocation();
//        getCityId();
    }

    private void startLocation(){
        showProgressDialog();
        mLocationManagerProxy.removeUpdates(this);
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, this);
    }

    private void setCharData(ArrayList<Number> max, ArrayList<Number> min){
        LineData lineData_max = new LineData();
        lineData_max.colorValue = Color.parseColor("#009688");
        lineData_max.dataList = max;

        LineData lineData_min = new LineData();
        lineData_min.colorValue = Color.parseColor("#6D6D6D");
        lineData_min.dataList = min;

        ArrayList<LineData> daArrayList = new ArrayList<LineData>();
        daArrayList.add(lineData_max);
        daArrayList.add(lineData_min);

        chartView.dataArray = daArrayList;
        chartView.showYLabels = false;
        chartView.showXLabels = false;
        chartView.drawYScaleLine = true;
        chartView.drawXScaleLine = false;
        chartView.YAxisDataCount = 4;
        chartView.XAxisDataCount = 7;
        chartView.title = "未来七天气温变化趋势";

        chartView.invalidate();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null&&aMapLocation.getAMapException().getErrorCode() == 0) {
            Log.i("test", aMapLocation.getLatitude() + "  " + aMapLocation.getLongitude());
            getCityId(aMapLocation.getLongitude() + "", aMapLocation.getLatitude() + "");
            ((MyApplication)getActivity().getApplication()).setLocation(aMapLocation.getAddress());
            ((MyApplication)getActivity().getApplication()).setLat(aMapLocation.getLatitude() + "");
            ((MyApplication)getActivity().getApplication()).setLon(aMapLocation.getLongitude() + "");
        }else{
            dismissProgressDialog();
            Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationManagerProxy.removeUpdates(this);
        mLocationManagerProxy.destroy();
    }


    private void getCityId(String lon, String lat){
        WeatherAPI.getGeo(getActivity(), lon, lat, new AsyncResponseHandler() {
            @Override
            public void onComplete(JSONObject content) {
                if ("0".equals(content.optString("status"))) {
                    JSONObject geo = content.optJSONObject("geo");
                    String id = geo.optString("id");
                    String district = geo.optString("district");
                    city.setText(district);
                    Log.i("test", "cityId:" + id);
                    getWeather(id);
                    ((MyApplication) getActivity().getApplication()).setCityId(id);
                } else {
                    dismissProgressDialog();
                    Toast.makeText(getActivity(), "获取城市解析失败", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onError(Throwable error, String content) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "获取城市失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getWeather(String cityId){
        WeatherAPI.getWeather2(getActivity(), cityId, Constants.Language.ZH_CN, new AsyncResponseHandler() {
            @Override
            public void onComplete(Weather content) {
                dismissProgressDialog();
                if (content != null) {
                    try {
                        parseWeather(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "获取天气解析失败", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onError(Throwable error, String content) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "获取天气失败", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void parseWeather(Weather weather){
        JSONObject fact = weather.getWeatherFactInfo();
        String[] l1 = fact.optString("l1").split("\\|");
        String[] l3 = fact.optString("l3").split("\\|");
        String[] l4 = fact.optString("l4").split("\\|");
        String[] l5 = fact.optString("l5").split("\\|");

        JSONArray time = weather.getTimeInfo(0);
        JSONObject time1 = time.optJSONObject(0);
        JSONObject time2 = time.optJSONObject(1);
        JSONObject time3 = time.optJSONObject(2);
        JSONObject time4 = time.optJSONObject(3);
        JSONObject time5 = time.optJSONObject(4);
        JSONObject time6 = time.optJSONObject(5);
        JSONObject time7 = time.optJSONObject(6);


        //forcast
        JSONArray forcast = weather.getWeatherForecastInfo(0);
        String fa1 = forcast.optJSONObject(0).optString("fa");
        String fa2 = forcast.optJSONObject(1).optString("fa");
        String fa3 = forcast.optJSONObject(2).optString("fa");
        String fa4 = forcast.optJSONObject(3).optString("fa");
        String fa5 = forcast.optJSONObject(4).optString("fa");
        String fa6 = forcast.optJSONObject(5).optString("fa");
        String fa7 = forcast.optJSONObject(6).optString("fa");

        ArrayList<Number> max = new ArrayList<Number>();
        ArrayList<Number> min = new ArrayList<Number>();
        for(int i = 1;i<8;i++){
            String fc = forcast.optJSONObject(i).optString("fc");
            String fd = forcast.optJSONObject(i).optString("fd");

            max.add(Integer.parseInt(fc));
            min.add(Integer.parseInt(fd));
        }

        temperature.setText(l1[l1.length - 1] + "°C");
        String windD = WeatherAPI.parseWindDirection(getActivity(), l4[l4.length-1], Constants.Language.ZH_CN);
        String windF = WeatherAPI.parseWindForce(getActivity(), l3[l3.length - 1], Constants.Language.ZH_CN);
        wend.setText(windD + windF);
        String number = String.valueOf(Integer.parseInt(l5[l5.length-1]));
        tv_weather.setText(WeatherAPI.parseWeather(getActivity(), number, Constants.Language.ZH_CN));



        text_weather_3.setText("周" + time3.optString("t4").substring(2, 3));
        text_weather_4.setText("周"+time4.optString("t4").substring(2,3));
        text_weather_5.setText("周" + time5.optString("t4").substring(2, 3));
        text_weather_6.setText("周" + time6.optString("t4").substring(2, 3));
        text_weather_7.setText("周" + time7.optString("t4").substring(2, 3));

        img_weather_1.setImageResource(parseWeatherImg(fa1));
        ((MyApplication)getActivity().getApplication()).setWeatherId(fa1);
        img_weather_2.setImageResource(parseWeatherImg(fa2));
        img_weather_3.setImageResource(parseWeatherImg(fa3));
        img_weather_4.setImageResource(parseWeatherImg(fa4));
        img_weather_5.setImageResource(parseWeatherImg(fa5));
        img_weather_6.setImageResource(parseWeatherImg(fa6));
        img_weather_7.setImageResource(parseWeatherImg(fa7));

        setCharData(max, min);


        //wawrning
        JSONArray warningList = weather.getWarningInfo();
        final JSONObject warning = warningList.optJSONObject(0);
        if(warning != null){
            String w1 = warning.optString("w1");
            String w2 = warning.optString("w2");
            String w4 = warning.optString("w4");
            String w5 = warning.optString("w5");
            String w6 = warning.optString("w6");
            String w7 = warning.optString("w7");
            String w8 = warning.optString("w8");
            String w9 = warning.optString("w9");

            int id = Integer.parseInt(warning.optString("w10").substring(13, 17));
            final String icon_text = WarningParser.getIcon(warning, getActivity());
            final String title_text = w1+w2+w5+w7+"预警";
            final String time_text = w8;
            final String context_text = w9;
            String str = WeatherAPI.parseWarning(getActivity(), w4 + w6);
            final String guide_text = str.split("\\|")[1].replace("<br>", "\n");
            final  String sm_text = str.split("\\|")[0];

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("icon", icon_text);
            map.put("title", title_text);
            map.put("time", time_text);
            map.put("content", context_text);
            map.put("guide", guide_text);
            map.put("sm", sm_text);
            ((MyApplication)getActivity().getApplication()).setWarning(map);

            warningImage.setImageResource(getActivity().getResources().getIdentifier(icon_text, "drawable", getActivity().getPackageName()));
            warningImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WarningActivity.class);
                    startActivity(intent);
                }
            });

            Intent intent = new Intent(getActivity(), WarningActivity.class);
            NotificationUtil.nofity(getActivity(), id, icon_text, title_text, context_text, intent);
        }

    }

    private int parseWeatherImg(String fa){
        int weather = R.drawable.sun_green;
        if("00".equals(fa)){
        }else if("01".equals(fa)){
            weather = R.drawable.cloudy_red;
        }else if("02".equals(fa)){
            weather = R.drawable.cloud_green;
        }else{
            weather = R.drawable.cloud_gray;
        }

        return weather;
    }


    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("正在搜索");
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }


    private void getCityId(){
        String cityId = "101270203";
        getWeather(cityId);
    }

}
