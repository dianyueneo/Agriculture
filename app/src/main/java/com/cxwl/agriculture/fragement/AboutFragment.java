package com.cxwl.agriculture.fragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
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

import java.io.File;
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
    private ProgressDialog progressDialog, loadProgressDialog;
    private AlertDialog alertDialog;
    private DownloadManager downloadManager;
    private DownloadTask downloadTask;
    private String url;
    private String name;
    private String latestVersion;
    private static final String NAME = "loadapk";

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
        CommonAPI.updateVersion(getActivity(), new AsyncResponseHandler() {
            @Override
            public void onComplete(JSONObject content) {
                dismissProgressDialog();
                if ("SUCCESS".equals(content.optString("status"))) {
                    JSONObject data = content.optJSONObject("data");
                    latestVersion = data.optString("latestVersion");
                    url = data.optString("url");
                    name = url.substring(url.lastIndexOf("/") + 1);
                    if (latestVersion.equals(getVersion())) {
                        Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_LONG).show();
                    } else {
                        String message = "是否从当前版本号" + getVersion() + "升级到版本号" + latestVersion;
                        showUpdateDialog(message, url);
                    }

                }else if("FAILURE".equals(content.optString("status"))){
                    Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_LONG).show();
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
                        if (!isSDuseable()) {
                            showSdCardDialog("存储卡不可用");
                            return;
                        }
                        if (getUseableStorage() < 10) {
                            showSdCardDialog("存储卡剩余的空间不足");
                            return;
                        }
                        downLoad(url, name);
                    }
                })
                .setNegativeButton("以后再说", null)
                .show();
    }

    private void showSdCardDialog(String msg){
        new AlertDialog.Builder(getActivity())
                .setTitle("更新提示")
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("好的", null)
                .show();
    }

    private void showTryUpdateDialog(){
        Log.i("test", "showTryUpdateDialog");
        if(alertDialog != null && alertDialog.isShowing()){
            return;
        }

        alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("更新提示")
                .setMessage("下载失败，是否重试？")
                .setCancelable(false)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        downLoad(url, name);
                    }
                })
                .setNegativeButton("取消", null)
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
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void showProgressDialog(String title){
        loadProgressDialog = new ProgressDialog(getActivity());
        loadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loadProgressDialog.setIndeterminate(false);
        loadProgressDialog.setCancelable(false);
        loadProgressDialog.setTitle(title);
        loadProgressDialog.setMessage("正在下载请稍后");
        loadProgressDialog.setMax(100);
        loadProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancleDownLoad();
            }
        });

        loadProgressDialog.show();
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
    private void dismissLoadProgressDialog(){
        if(loadProgressDialog != null){
            loadProgressDialog.dismiss();
        }
    }

    private void downLoad(String url, String name){
        String path = getAPKPath();
        File file = new File(path);

        String apk = getDownload(latestVersion);
        if(apk != null){
            if(file.exists()){
                replaceLaunchApk();
                return;
            }
        }

        if(file.exists()){
            file.delete();
        }

        downloadManager = new DownloadManager(getActivity());
        downloadTask = new DownloadTask(getActivity());
        downloadTask.setUrl(url);
        downloadTask.setPath(path);
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
            if(loadProgressDialog != null){
                loadProgressDialog.setProgress(values[0]);
            }
        }

        @Override
        public void onSuccess(DownloadTask downloadTask) {
            super.onSuccess(downloadTask);
            dismissLoadProgressDialog();
            saveDownload(latestVersion, name);
            replaceLaunchApk();
        }

        @Override
        public void onError(Throwable thr) {
            super.onError(thr);
            Log.i("test", "onError...");
            dismissLoadProgressDialog();
            showTryUpdateDialog();
        }
    };

    private void saveDownload(String version, String name){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(version, name);
        editor.commit();
    }

    private String getDownload(String version){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(version, null);
    }

    private String getSDCartPath(){
        return Environment.getExternalStorageDirectory().getPath();
    }

    private boolean isSDuseable(){
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private long getUseableStorage(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            StatFs statFs = new StatFs(getSDCartPath());

            long abl = statFs.getAvailableBlocks();
            long  bsl = statFs.getBlockSize();
            return abl*bsl/1024/1024;
        }else{
            return -1;
        }
    }

    //启动安装替换apk
    private void replaceLaunchApk() {
        String apkpath = getAPKPath();
        File file = new File(apkpath);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
            Log.e("test", "File not exsit:" + apkpath);
        }
    }

    private String getAPKPath(){
        return getActivity().getExternalCacheDir().getPath() + File.separator + name;
    }
}
