package com.cxwl.agriculture.fragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cxwl.agriculture.IntroActivity;
import com.cxwl.agriculture.R;
import com.cxwl.agriculture.netInterface.NetUtil;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadTask;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.weather.api.CommonAPI;
import cn.com.weather.listener.AsyncResponseHandler;

/**
 * Created by admin on 7/16/15.
 */
public class AboutFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private RequestQueue queue;
    private RetryPolicy retryPolicy;
    private TextView introduction;
    private TextView checkupdate;
    private ProgressDialog progressDialog;
    private DownloadManager downloadManager;
    private DownloadTask downloadTask;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("test", "onCreateView======"+this.getClass().getSimpleName());
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
        checkupdate = (TextView)rootView.findViewById(R.id.checkupdate);
        introduction.setOnClickListener(this);
        checkupdate.setOnClickListener(this);
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
            case R.id.checkupdate:
                checkUpdate();
                break;
            default:
                break;
        }
    }

    private void checkUpdate(){
        showProgressDialog();
        CommonAPI.updateVersion(getActivity(), new AsyncResponseHandler(){
            @Override
            public void onComplete(JSONObject content) {
                dismissProgressDialog();
                if("SUCCESS".equals(content.optString("status"))){
                    JSONObject data = content.optJSONObject("data");
                    String latestVersion = data.optString("latestVersion");
                    url = data.optString("url");
                    if(latestVersion.equals(getVersion())){
                        Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_LONG).show();
                    }else{
                        String message = "是否从当前版本号"+getVersion()+"升级到版本号"+latestVersion;
                        showUpdateDialog(message, url);
                    }

                }


            }

            @Override
            public void onError(Throwable error, String content) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showUpdateDialog(String message, final String url){
        new AlertDialog.Builder(getActivity())
                .setTitle("更新提示")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //TODO
                        downLoad(url);
                    }
                })
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void showTryUpdateDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle("更新提示")
                .setMessage("下载失败，是否重试？")
                .setCancelable(false)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //TODO
                        downLoad(url);
                    }
                })
                .setNegativeButton("去掉", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private String getVersion(){
        String version = null;
        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }finally {
            return version;
        }
    }


    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void showProgressDialog(String title){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setTitle(title);
        progressDialog.setMessage("正在下载请稍后");
        progressDialog.setProgress(100);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancleDownLoad();
            }
        });

        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void downLoad(String url){
        downloadManager = new DownloadManager(getActivity());
        downloadTask = new DownloadTask(getActivity());
        downloadTask.setUrl(url);
        downloadManager.add(downloadTask, listener);
        downloadManager.start(downloadTask, listener);
    }

    private void cancleDownLoad(){
        downloadManager.stop(downloadTask, listener);
    }

    private DownloadListener listener = new DownloadListener<Integer, DownloadTask>(){
        @Override
        public void onStart() {
            super.onStart();
            showProgressDialog("下载");
        }

        @Override
        public void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(progressDialog != null){
                progressDialog.setProgress(values[0]);
            }
        }

        @Override
        public void onSuccess(DownloadTask downloadTask) {
            super.onSuccess(downloadTask);
            dismissProgressDialog();
        }

        @Override
        public void onError(Throwable thr) {
            super.onError(thr);
            showTryUpdateDialog();
        }
    };
}
