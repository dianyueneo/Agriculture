package com.cxwl.agriculture.fragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("test", "onCreateView======" + this.getClass().getSimpleName());
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
                    String url = data.optString("url");
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

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }


    /**
     * 获取外置SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /*
  * 返回SD卡可用容量  --#
  */
    private static long getUsableStorage() {
        String sDcString = android.os.Environment.getExternalStorageState();

        if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File pathFile = android.os.Environment
                    .getExternalStorageDirectory();

            android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();

            long nBlocSize = statfs.getBlockSize();

            // 计算 SDCard 剩余大小MB
            return nAvailaBlock * nBlocSize / 1024 / 1024;
        } else {
            return -1;
        }


    }

    //启动安装替换apk
    private void ReplaceLaunchApk(String apkpath) {
        // TODO Auto-generated method stub
        File file = new File(apkpath);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
        }
    }

}
