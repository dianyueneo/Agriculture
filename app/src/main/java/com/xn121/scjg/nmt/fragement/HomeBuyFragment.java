package com.xn121.scjg.nmt.fragement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xn121.scjg.nmt.CategoryActivity;
import com.xn121.scjg.nmt.MapActivity;
import com.xn121.scjg.nmt.MyApplication;
import com.xn121.scjg.nmt.ProvinceActivity;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.SellStep.SellStep;
import com.xn121.scjg.nmt.bean.Profit;
import com.xn121.scjg.nmt.netInterface.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hongge on 15/7/18.
 */
public class HomeBuyFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private TextView qidian, xzdd, btn_sf, btn_cs;
    private TextView zhongdian, xzzd, btn_sf2;
    private TextView shangpin, xzcp, btn_sc, btn_cp;
    private TextView dangdijiage, btn_cx, tv_pf, tv_pf_c, tv_ls, tv_ls_c;
    private TextView spcbsl, xiaoshou, xiaoshou_cb;
    private EditText xs_sl, xs_cb;
    private TextView yunshu, btn_cl, btn_ry;
    private TextView qita, gongren, yuan1, cailiao, yuan2, qtfy, yuan3;
    private EditText et_gr, et_cl, et_qt;
    private ImageView progress1, progress2, progress3, progress4, progress5, progress6, progress7;
    private TextView btn_hqxl;

    private Object[] step1 = null;
    private Object[] step2 = null;
    private Object[] step3 = null;
    private Object[] step4 = null;
    private Object[] step5 = null;
    private Object[] step6 = null;
    private Object[] step7 = null;
    private Object[] step8 = null;

    private boolean textchanged1 = false;
    private boolean textchanged2 = false;
    private boolean textchanged3 = false;
    private boolean textchanged4 = false;
    private boolean textchanged5 = false;

    private SellStep sellStep1, sellStep2, sellStep3, sellStep4, sellStep5, sellStep6, sellStep7, sellStep8;

    private String provinceId_start, marketId, provinceId_end, provinceName_end, goodsPinin, carId, fuelId;

    private RequestQueue requestQueue;
    private RetryPolicy retryPolicy;

    private ProgressDialog progressDialog;

    private String type = "acquisition";//acquisition

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home_sell, null);
            initView();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initView(){
        //step1
        progress1 = (ImageView)rootView.findViewById(R.id.progress1);
        qidian = (TextView)rootView.findViewById(R.id.qidian);
        xzdd = (TextView)rootView.findViewById(R.id.xzdd);
        btn_sf = (TextView)rootView.findViewById(R.id.btn_sf);
        btn_cs = (TextView)rootView.findViewById(R.id.btn_cs);

        btn_sf.setOnClickListener(this);
        btn_cs.setOnClickListener(this);

        step1 = new Object[]{progress1, qidian, xzdd, btn_sf, btn_cs};
        sellStep1 = new SellStep(this.getActivity(), step1, SellStep.SELECT);

        //step2
        progress2 = (ImageView)rootView.findViewById(R.id.progress2);
        zhongdian = (TextView)rootView.findViewById(R.id.zhongdian);
        xzzd = (TextView)rootView.findViewById(R.id.xzzd);
        btn_sf2 = (TextView)rootView.findViewById(R.id.btn_sf2);

        btn_sf2.setOnClickListener(this);

        step2 = new Object[]{progress2, zhongdian, xzzd, btn_sf2};
        sellStep2 = new SellStep(this.getActivity(), step2, SellStep.UNSELECT);

        //step3
        progress3 = (ImageView)rootView.findViewById(R.id.progress3);
        shangpin = (TextView)rootView.findViewById(R.id.shangpin);
        xzcp = (TextView)rootView.findViewById(R.id.xzcp);
        btn_sc = (TextView)rootView.findViewById(R.id.btn_sc);
        btn_cp = (TextView)rootView.findViewById(R.id.btn_cp);

        btn_sc.setOnClickListener(this);
        btn_cp.setOnClickListener(this);

        step3 = new Object[]{progress3, shangpin, xzcp, btn_sc, btn_cp};
        sellStep3 = new SellStep(this.getActivity(), step3, SellStep.UNSELECT);

        //step4
        progress4 = (ImageView)rootView.findViewById(R.id.progress4);
        dangdijiage = (TextView)rootView.findViewById(R.id.dangdijiage);
        btn_cx = (TextView)rootView.findViewById(R.id.btn_cx);
        tv_pf = (TextView)rootView.findViewById(R.id.tv_pf);
        tv_pf_c = (TextView)rootView.findViewById(R.id.tv_pf_c);
        tv_ls = (TextView)rootView.findViewById(R.id.tv_ls);
        tv_ls_c = (TextView)rootView.findViewById(R.id.tv_ls_c);

        btn_cx.setOnClickListener(this);

        step4 = new Object[]{progress4, dangdijiage, btn_cx, tv_pf, tv_pf_c, tv_ls, tv_ls_c};
        sellStep4 = new SellStep(this.getActivity(), step4, SellStep.UNSELECT);

        //step5
        progress5 = (ImageView)rootView.findViewById(R.id.progress5);
        spcbsl = (TextView)rootView.findViewById(R.id.spcbsl);
        xiaoshou = (TextView)rootView.findViewById(R.id.xiaoshou);
        xiaoshou_cb = (TextView)rootView.findViewById(R.id.xiaoshou_cb);
        xs_sl = (EditText)rootView.findViewById(R.id.xs_sl);
        xs_cb = (EditText)rootView.findViewById(R.id.xs_cb);

        xs_sl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length() > 0){
                    textchanged1 = true;
                    if(textchanged1 && textchanged2){
                        sellStep5.complete();
                    }
                }
            }
        });

        xs_cb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length() > 0){
                    textchanged2 = true;
                    if(textchanged1 && textchanged2){
                        sellStep5.complete();
                    }
                }
            }
        });

        step5 = new Object[]{progress5, spcbsl, xiaoshou, xiaoshou_cb, xs_sl, xs_cb};
        sellStep5 = new SellStep(this.getActivity(), step5, SellStep.UNSELECT);

        //step6
        progress6 = (ImageView)rootView.findViewById(R.id.progress6);
        yunshu = (TextView)rootView.findViewById(R.id.yunshu);
        btn_cl = (TextView)rootView.findViewById(R.id.btn_cl);
        btn_ry = (TextView)rootView.findViewById(R.id.btn_ry);

        btn_cl.setOnClickListener(this);
        btn_ry.setOnClickListener(this);

        step6 = new Object[]{progress6, yunshu, btn_cl, btn_ry};
        sellStep6 = new SellStep(this.getActivity(), step6, SellStep.UNSELECT);

        //step7
        progress7 = (ImageView)rootView.findViewById(R.id.progress7);
        qita = (TextView)rootView.findViewById(R.id.qita);
        gongren = (TextView)rootView.findViewById(R.id.gongren);
        yuan1 = (TextView)rootView.findViewById(R.id.yuan1);
        cailiao = (TextView)rootView.findViewById(R.id.cailiao);
        yuan2 = (TextView)rootView.findViewById(R.id.yuan2);
        qtfy = (TextView)rootView.findViewById(R.id.qtfy);
        yuan3 = (TextView)rootView.findViewById(R.id.yuan3);

        et_gr = (EditText)rootView.findViewById(R.id.et_gr);
        et_cl = (EditText)rootView.findViewById(R.id.et_cl);
        et_qt = (EditText)rootView.findViewById(R.id.et_qt);

        et_gr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length() > 0){
                    textchanged3 = true;
                    if(textchanged3 && textchanged4 && textchanged5){
                        sellStep7.complete();
                    }
                }
            }
        });

        et_cl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length() > 0){
                    textchanged4 = true;
                    if(textchanged3 && textchanged4 && textchanged5){
                        sellStep7.complete();
                    }
                }
            }
        });

        et_qt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().length() > 0){
                    textchanged5 = true;
                    if(textchanged3 && textchanged4 && textchanged5){
                        sellStep7.complete();
                    }
                }
            }
        });

        step7 = new Object[]{progress7, qita, gongren, yuan1, cailiao, yuan2, qtfy, yuan3, et_gr, et_cl, et_qt};
        sellStep7 = new SellStep(this.getActivity(), step7, SellStep.UNSELECT);

        //step8
        btn_hqxl = (TextView)rootView.findViewById(R.id.btn_hqxl);

        btn_hqxl.setOnClickListener(this);

        step8 = new Object[]{btn_hqxl};
        sellStep8 = new SellStep(this.getActivity(), step8, SellStep.UNSELECT);

        sellStep1.setHandler(sellStep2);
        sellStep2.setHandler(sellStep3);
        sellStep3.setHandler(sellStep4);
        sellStep4.setHandler(sellStep5);
        sellStep5.setHandler(sellStep6);
        sellStep6.setHandler(sellStep7);
        sellStep7.setHandler(sellStep8);

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sf:
                Intent intent_sf = new Intent(this.getActivity(), ProvinceActivity.class);
                intent_sf.putExtra("requestCode", HomeFragment.REQUEST_CODE_PROVINCESTART);
                startActivityForResult(intent_sf, HomeFragment.REQUEST_CODE_PROVINCESTART);
                break;
            case R.id.btn_sf2:
                Intent intent_sf2 = new Intent(this.getActivity(), ProvinceActivity.class);
                intent_sf2.putExtra("requestCode", HomeFragment.REQUEST_CODE_PROVINCEEND);
                startActivityForResult(intent_sf2, HomeFragment.REQUEST_CODE_PROVINCEEND);
                break;
            case R.id.btn_sc:
                Intent intent_sc = new Intent(this.getActivity(), CategoryActivity.class);
                startActivityForResult(intent_sc, HomeFragment.REQUEST_CODE_CATEGORY_START);
                break;
            case R.id.btn_cx:
                getPrice();
                break;
            case R.id.btn_cl:
                showCarDialog();
                break;
            case R.id.btn_ry:
                showFuelDialog();
                break;
            case R.id.btn_hqxl:
                getProfitStatement();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        requestQueue = Volley.newRequestQueue(activity);
        retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }


    private void getPrice(){

        showProgressDialog();

        Calendar calendar = Calendar.getInstance();
        String areaid = provinceId_start + goodsPinin + "market" + marketId + calendar.get(Calendar.YEAR) + "m" +(calendar.get(Calendar.MONTH)+1);
        Log.i("test", "=========="+areaid);
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String type = "xn121";
        String publicKey = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID);
        String key = NetUtil.getSignature(publicKey);
        String url = String.format(NetUtil.GETPRICE, areaid, type, date, NetUtil.APPID.substring(0,6)+"&key="+key);
        Log.i("test", "==========" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv_pf_c.setText(getPrice(response));
                tv_ls_c.setText(getPrice(response));
                sellStep4.complete();
                dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);

    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HomeFragment.REQUEST_CODE_PROVINCESTART && resultCode == HomeFragment.RESULT_CODE_PROVINCE){
            btn_sf.setText(data.getStringExtra("provinceName"));
            btn_cs.setText(data.getStringExtra("marketName"));
            provinceId_start = data.getStringExtra("provinceId");
            marketId = data.getStringExtra("marketId");
            sellStep1.complete();
        }else if(requestCode == HomeFragment.REQUEST_CODE_PROVINCEEND && resultCode == HomeFragment.RESULT_CODE_PROVINCE){
            btn_sf2.setText(data.getStringExtra("provinceName"));
            provinceName_end = data.getStringExtra("provinceName");
            provinceId_end = data.getStringExtra("provinceId");
            sellStep2.complete();
        }else if(requestCode == HomeFragment.REQUEST_CODE_CATEGORY_START && resultCode == HomeFragment.RESULT_CODE_CATEGORY){
            btn_sc.setText(data.getStringExtra("category"));
            btn_cp.setText(data.getStringExtra("name"));
            goodsPinin = data.getStringExtra("pinyin");
            sellStep3.complete();
        }

    }

    private String getPrice(JSONObject response){
        String str = "$";
        try {
            boolean status = TextUtils.isEmpty(response.optString("status"));
            if(status) {
                JSONArray jsonArray = response.getJSONArray("days");
                str += jsonArray.getJSONObject(jsonArray.length() - 1).getString("price");
            }else{
                str += "00.00";
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return str;
    }

    private void showCarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("车辆类型");
        final String[] carlist = {"微型货车", "轻型货车", "中型货车", "重型货车"};
        builder.setItems(carlist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carId = which+1+"";
                btn_cl.setText(carlist[which]);
            }
        });
        builder.show();
    }

    private void showFuelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("燃油类型");
        final String[] carlist = {"93汽油", "0柴油", "天然气", "90汽油", "97汽油", "-10柴油", "-20柴油"};
        builder.setItems(carlist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fuelId = which + 1 + "";
                btn_ry.setText(carlist[which]);
                sellStep6.complete();
            }
        });
        builder.show();
    }

    private void getProfitStatement(){
        showProgressDialog();
        String corpname = goodsPinin.substring(goodsPinin.indexOf("_")+1,goodsPinin.length());
        String price = xs_cb.getText().toString();
        String number = xs_sl.getText().toString();
        String othercosts = Float.parseFloat(et_gr.getText().toString())+Float.parseFloat(et_cl.getText().toString())+Float.parseFloat(et_qt.getText().toString())+"";
        String url = String.format(NetUtil.GETPROFITSTATEMENT, type, corpname, marketId, provinceId_end, price, number, carId, fuelId, othercosts);
        Log.i("test", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                parseProfitStatement(response);
                sellStep8.complete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);

    }

    private void parseProfitStatement(JSONObject jsonObject){
        boolean success = false;
        try {
            String status = jsonObject.getString("status");
            if("success".equals(status)){
                JSONObject startpoint = jsonObject.getJSONObject("startpoint");
                Double lon = Double.parseDouble(startpoint.getString("lon"));
                Double lat = Double.parseDouble(startpoint.getString("lat"));
                String name_start = startpoint.getString("name");

                JSONArray list = jsonObject.getJSONArray("list");
                Profit profit;
                ArrayList<Profit> arrayList = new ArrayList<Profit>();
                int size = list.length();
                if(size > 0){
                    for(int i=0;i<size;i++){
                        JSONObject json = list.getJSONObject(i);
                        profit = new Profit();
                        profit.setName_end(json.getString("name"));
                        profit.setProfit(json.getDouble("profit"));
                        profit.setDistance(json.getDouble("distance"));
                        profit.setFuelprice(json.getDouble("fuelprice"));
                        profit.setTime(json.getDouble("time"));
                        profit.setPrice(json.getDouble("price"));
                        profit.setLon_end(Double.parseDouble(json.getString("lon")));
                        profit.setLat_end(Double.parseDouble(json.getString("lat")));

                        profit.setName_start(name_start);
                        profit.setLon_start(lon);
                        profit.setLat_start(lat);

                        arrayList.add(profit);
                    }

                    ((MyApplication)getActivity().getApplication()).setProfitList(arrayList);

                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);

                    success = true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(!success){
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
        }
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

}
