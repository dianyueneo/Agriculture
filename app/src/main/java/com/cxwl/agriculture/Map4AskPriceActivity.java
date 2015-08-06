package com.cxwl.agriculture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.cxwl.agriculture.bean.Price;

import java.util.ArrayList;


/**
 * Created by hongge on 15/7/25.
 */
public class Map4AskPriceActivity extends FragmentActivity implements View.OnClickListener, AMap.OnMapLoadedListener, AMap.OnMarkerClickListener{

    private MapView mapView;
    private AMap aMap;

    private ImageView back;
    private ProgressDialog progDialog;

    private ArrayList<Price> list;

    private ArrayList<LatLng> latLngs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_askprice);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if(aMap == null){
            aMap = mapView.getMap();
        }

        aMap.setOnMapLoadedListener(this);
        aMap.setOnMarkerClickListener(this);

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);


        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("list");

        startDraw();
    }

    public void startDraw(){
        showProgressDialog();

        latLngs = new ArrayList<LatLng>();

        for(Price price : list){
            LatLng point = new LatLng(price.getLat(), price.getLon());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point);
            markerOptions.title(price.getAddress());

            latLngs.add(point);

            aMap.addMarker(markerOptions);
        }

        dissmissProgressDialog();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(LatLng latLng: latLngs){
            builder = builder.include(latLng);
        }

        LatLngBounds bounds = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
