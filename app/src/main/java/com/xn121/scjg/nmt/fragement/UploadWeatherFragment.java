package com.xn121.scjg.nmt.fragement;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.xn121.scjg.nmt.MyApplication;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.adapter.WeatherListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.weather.api.CrowdWeatherAPI;
import cn.com.weather.api.WeatherAPI;
import cn.com.weather.beans.Weather;
import cn.com.weather.constants.Constants;
import cn.com.weather.http.RequestParams;
import cn.com.weather.listener.AsyncResponseHandler;

/**
 * Created by admin on 7/16/15.
 */
public class UploadWeatherFragment extends Fragment implements AMapLocationListener,SensorEventListener{

    private View rootView;

    private LocationManagerProxy mLocationManagerProxy;

    private TextView address, qiya, tishi;
    private EditText nq_content;
    private GridView gridView;
    private WeatherListAdapter adapter;

    private SensorManager sensorManager;
    private Sensor sensor;

    private float pressure = 0;
    private String weatherId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_uploadweather, null);
            init();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void init(){
        mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
        mLocationManagerProxy.setGpsEnable(false);

        address = (TextView)rootView.findViewById(R.id.address);
        qiya = (TextView)rootView.findViewById(R.id.qiya);
        nq_content = (EditText)rootView.findViewById(R.id.nq_content);
        gridView = (GridView)rootView.findViewById(R.id.gridview);
        tishi = (TextView)rootView.findViewById(R.id.tishi);

        adapter = new WeatherListAdapter(getActivity());
        adapter.setCodes(getWeatherList());
        gridView.setAdapter(adapter);


        String location = ((MyApplication)getActivity().getApplication()).getLocation();
        if(location == null){
            startLocation();
        }else{
            address.setText(location);
        }

        String code = ((MyApplication)getActivity().getApplication()).getWeatherId();
        if(code != null){
            checkButton(code);
        }

        initSerSor();
    }

    private void checkButton(String weatherId){
        int id = getResources().getIdentifier("weather"+weatherId, "id", getActivity().getPackageName());
    }


    private void startLocation(){
        mLocationManagerProxy.removeUpdates(this);
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, this);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null&&aMapLocation.getAMapException().getErrorCode() == 0) {
            Log.i("test", aMapLocation.getLatitude() + "  " + aMapLocation.getLongitude());

            ((MyApplication)getActivity().getApplication()).setLocation(aMapLocation.getAddress());
            ((MyApplication)getActivity().getApplication()).setLat(aMapLocation.getLatitude() + "");
            ((MyApplication)getActivity().getApplication()).setLon(aMapLocation.getLongitude() + "");

            if(((MyApplication)getActivity().getApplication()).getCityId() == null){
                getCityId(aMapLocation.getLongitude() + "", aMapLocation.getLatitude() + "");
            }
        }else{
            Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void getCityId(String lon, String lat){
        WeatherAPI.getGeo(getActivity(), lon, lat, new AsyncResponseHandler() {
            @Override
            public void onComplete(JSONObject content) {
                if ("0".equals(content.optString("status"))) {
                    JSONObject geo = content.optJSONObject("geo");
                    String id = geo.optString("id");
                    Log.i("test", "cityId:" + id);

                    ((MyApplication) getActivity().getApplication()).setCityId(id);

                    if (((MyApplication) getActivity().getApplication()).getWeatherId() == null) {
                        getWeather(id);
                    }
                } else {
                    Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onError(Throwable error, String content) {
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getWeather(String cityId){
        WeatherAPI.getWeather2(getActivity(), cityId, Constants.Language.ZH_CN, new AsyncResponseHandler() {
            @Override
            public void onComplete(Weather content) {
                if (content != null) {
                    parseWeather(content);
                } else {
                    Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onError(Throwable error, String content) {
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseWeather(Weather weather){
        JSONObject fact = weather.getWeatherFactInfo();
        String[] l5 = fact.optString("l5").split("\\|");
        String weatherId = l5[l5.length-1];

        ((MyApplication)getActivity().getApplication()).setWeatherId(weatherId);
    }

    private void initSerSor(){
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(sensor == null){
            tishi.setText("您的手机不支持气压传感器");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
            qiya.setText(event.values[0]+"hPa");
            pressure = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void uploadWeather(){

        JSONObject weather = new JSONObject();
        try {
            weather.put("code", weatherId);
            weather.put("pressure", pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams();

        params.put("areaId", ((MyApplication) getActivity().getApplication()).getCityId());
        params.put("lng", ((MyApplication) getActivity().getApplication()).getLon());
        params.put("lat", ((MyApplication) getActivity().getApplication()).getLat());
        params.put("position", ((MyApplication) getActivity().getApplication()).getLocation());
        params.put("weather", weather.toString());

        CrowdWeatherAPI.uploadMyWeather(getActivity(), params, new AsyncResponseHandler() {
            @Override
            public void onComplete(JSONObject content) {
                String str = "上传失败";
                if (content != null) {
                    if ("SUCCESS".equals(content.optString("status"))) {
                        str = "上传成功";
                    }
                    ;
                }
                Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> getWeatherList(){
        ArrayList<String> list = new ArrayList<String>();

        //晴、多云、阴
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("-1");

        //雨
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("19");
        list.add("-1");
        list.add("-1");

        //雪、小到中雪、中到大雪、大到暴雪
        list.add("33");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("06");
        list.add("13");
        list.add("-1");

        //浮尘、扬沙、沙尘暴、强沙尘暴
        list.add("29");
        list.add("30");
        list.add("20");
        list.add("31");

        //雾、大雾、浓雾、强浓雾、特强浓雾
        list.add("18");
        list.add("57");
        list.add("32");
        list.add("49");
        list.add("58");
        list.add("-1");
        list.add("-1");
        list.add("-1");

        //霾、中度霾、重度霾、严重霾
        list.add("53");
        list.add("54");
        list.add("55");
        list.add("56");

        return list;
    }

}
