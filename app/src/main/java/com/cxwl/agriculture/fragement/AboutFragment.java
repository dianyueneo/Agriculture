package com.cxwl.agriculture.fragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private int retry;
    private int TryDownTotal=2;//下载失败再次尝试的次数
    private ProgressDialog pbar;
    private String fileName = "agriculture.apk";
    private String path;
    private int fileTotalSize = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 3:
                    ReplaceLaunchApk(path+"/"+fileName);
                    break;
                case 4:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("错误")
                            .setMessage("请插入外部存储卡")
                            .setCancelable(false)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                    break;
                case 5:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("错误")
                            .setMessage("存储卡剩余的空间不足")
                            .setCancelable(false)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                    break;
                default:
                    break;
            }
        }
    };

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

    private void getTotalSize(String url){
        try {
            URL u = new URL(url);
            HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
            fileTotalSize =  urlcon.getContentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                        predownloadapk(url);
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

    private void predownloadapk(String apkDownUrl)
    {
        retry=0;
        getTotalSize(apkDownUrl);
        //创建文件
        downloadapk(apkDownUrl);
    }

    private void downloadapk(String apkDownUrl)
    {
        //
        pbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pbar.setMessage("正在下载中，请稍后...");
        pbar.setCancelable(false);
        pbar.setMax(100);
        pbar.setProgress(0);
        pbar.show();

        new DownloadFilesTask().execute(apkDownUrl);

    }

    class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

        private URL url; // 资源位置
        private long beginPosition; // 开始位置


        @Override
        protected Long doInBackground(String... params) {


            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            RandomAccessFile output = null;
            try {
                // 获取连接对象h


                //创建包名的文件夹
                boolean hasSD = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);          //只检验是否可读且可
                if (!hasSD) {

                    Message msg = Message.obtain(mHandler);
                    msg.what = 4;
                    mHandler.sendMessage(msg);
                    return (long) 0;
                }
                long availablesize = getUsableStorage();
                if (availablesize < 10) {

                    Message msg = Message.obtain(mHandler);
                    msg.what = 5;
                    mHandler.sendMessage(msg);
                    return (long) 0;
                }

                String sddir = getSDCardPath();
                String packageName = getActivity().getPackageName();

                path = sddir + "/" + packageName;
                File destDir = new File(sddir + "/" + packageName);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                File outFile = new File(sddir + "/" + packageName + "/" + fileName);
                if (!outFile.exists()) {
                    try {
                        //在指定的文件夹中创建文件
                        outFile.createNewFile();
                    } catch (Exception e) {
                    }
                }

                // 通过文件创建输出流对象RandomAccessFile,r:读 w:写 d:删除
                output = new RandomAccessFile(outFile, "rw");
                // 设置写入位置

                long lenth = outFile.length();


                if (fileTotalSize == lenth) {

                    //下载完的处理
                    publishProgress(100);
                    Message msg = Message.obtain(mHandler);
                    msg.what = 3;
                    mHandler.sendMessage(msg);
                    return lenth;

                } else {
                    beginPosition = lenth;
                }

                // 设置断点续传的开始位置
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setAllowUserInteraction(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(4000);
                // httpURLConnection.setRequestProperty("User-Agent","NetFox");
                httpURLConnection.setRequestProperty("Range", "bytes=" + beginPosition + "-");
                inputStream = httpURLConnection.getInputStream();

                output.seek(beginPosition);
                byte[] buf = new byte[1024 * 100];
                int readsize = 0;
                // 进行循环输出
                while ((readsize = inputStream.read(buf)) != -1) {
                    output.write(buf, 0, readsize);
                    beginPosition += readsize;
                    publishProgress((int) (beginPosition * 100.0f / fileTotalSize));
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        if (output != null) {
                            output.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return beginPosition;
        }

        /**
         * 更新下载进度，当publishProgress方法被调用的时候就会自动来调用这个方法
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            pbar.setProgress(values[0]);
            if (values[0] == 100) {
                Message msg = Message.obtain(mHandler);
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        }

        //下载完的回调
        @Override
        protected void onPostExecute(Long size) {

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
