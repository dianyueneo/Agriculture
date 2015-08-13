package com.cxwl.agriculture;

import android.app.ProgressDialog;
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
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.cxwl.agriculture.bean.Profit;
import com.cxwl.agriculture.fragement.MapProfitFragment;
import com.cxwl.agriculture.fragement.MapShortFragment;
import com.cxwl.agriculture.util.ToastUtil;

import java.util.List;


/**
 * Created by hongge on 15/7/25.
 */
public class MapActivity extends FragmentActivity implements RouteSearch.OnRouteSearchListener, View.OnClickListener{

    private MapView mapView;
    private AMap aMap;
    private RouteSearch routeSearch;
    private DriveRouteResult driveRouteResult;

    private ProgressDialog progDialog;

    private SlidingDrawer slidingDrawer;
    private ImageView imageView, back;

    private List<Profit> list;

    private Class[] fragmentArray = {MapProfitFragment.class, MapShortFragment.class};
    private String[] textArray = {"利润排名推荐", "最短路程"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();

        list = ((MyApplication)getApplication()).getProfitList();

        if(list != null && list.size() > 0){
            Profit profit = list.get(0);
            startDriveRouteQuery(profit);
        }

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

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);

    }

    private View getItemView(int i){
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_map, null);

        TextView tv = (TextView)view.findViewById(R.id.textview);
        tv.setText(textArray[i]);

        return view;
    }


    public void startDriveRouteQuery(Profit profit){
        showProgressDialog();
        LatLonPoint startPoint = new LatLonPoint(profit.getLat_start(), profit.getLon_start());
        LatLonPoint endPoint = new LatLonPoint(profit.getLat_end(), profit.getLon_end());
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
