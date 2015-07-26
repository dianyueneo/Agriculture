package com.xn121.scjg.nmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.xn121.scjg.nmt.fragement.MapProfitFragment;
import com.xn121.scjg.nmt.fragement.MapShortFragment;
import com.xn121.scjg.nmt.util.ToastUtil;


/**
 * Created by hongge on 15/7/25.
 */
public class MapActivity extends FragmentActivity implements RouteSearch.OnRouteSearchListener, PoiSearch.OnPoiSearchListener{

    private MapView mapView;
    private AMap aMap;
    private RouteSearch routeSearch;
    private DriveRouteResult driveRouteResult;
    private PoiSearch.Query endSearchQuery;

    private ProgressDialog progDialog;

    private SlidingDrawer slidingDrawer;
    private ImageView imageView;

    private double start_lat, start_lon;
    private String end;

    private Class[] fragmentArray = {MapProfitFragment.class, MapShortFragment.class};
    private String[] textArray = {"利润排名推荐", "最短路程"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        start_lon = intent.getDoubleExtra("start_lon", 0);
        start_lat = intent.getDoubleExtra("start_lat", 0);
        end = intent.getStringExtra("end");

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
        startSerachEndPoint();
    }

    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
        }
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);

        imageView = (ImageView)findViewById(R.id.handler);
        slidingDrawer = (SlidingDrawer)findViewById(R.id.slidingDrawer);
        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                imageView.setImageResource(R.drawable.up);
            }
        });

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                imageView.setImageResource(R.drawable.down);
            }
        });

        FragmentTabHost fragmentTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);
        if(Build.VERSION.SDK_INT > 10){
            fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }

        for(int i=0;i<fragmentArray.length;i++){
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(textArray[i]).setIndicator(getItemView(i));
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background_map);
        }

    }

    private View getItemView(int i){
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_map, null);

        TextView tv = (TextView)view.findViewById(R.id.textview);
        tv.setText(textArray[i]);

        return view;
    }

    private void startSerachEndPoint(){
        showProgressDialog();
        endSearchQuery = new PoiSearch.Query(end, "", ""); // 第一个参数表示查询关键字，第二参数表示poi搜索类型，第三个参数表示城市区号或者城市名
        endSearchQuery.setPageNum(0);// 设置查询第几页，第一页从0开始
        endSearchQuery.setPageSize(1);// 设置每页返回多少条数据

        PoiSearch poiSearch = new PoiSearch(MapActivity.this,
                endSearchQuery);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn(); // 异步poi查询
    }

    private void startDriveRouteQuery(LatLonPoint endPoint){
        LatLonPoint startPoint = new LatLonPoint(start_lat, start_lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
        routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
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

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this, aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(MapActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(MapActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(MapActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(MapActivity.this, getString(R.string.error_other)
                    + rCode);
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

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
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 0) {// 返回成功
            if (result != null && result.getQuery() != null
                    && result.getPois() != null && result.getPois().size() > 0) {// 搜索poi的结果
                if (result.getQuery().equals(endSearchQuery)) {
                    PoiItem poiItems = result.getPois().get(0);// 取得poiitem数据
                    startDriveRouteQuery(poiItems.getLatLonPoint());
                }
            } else {
                ToastUtil.show(MapActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(MapActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(MapActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(MapActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {

    }
}
