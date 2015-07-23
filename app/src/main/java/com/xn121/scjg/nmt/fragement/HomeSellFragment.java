package com.xn121.scjg.nmt.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xn121.scjg.nmt.ProvinceActivity;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.SellStep.SellStep;
import com.xn121.scjg.nmt.bean.Market;
import com.xn121.scjg.nmt.bean.Province;

/**
 * Created by hongge on 15/7/18.
 */
public class HomeSellFragment extends Fragment implements View.OnClickListener{

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

    private String provinceId, marketId;

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
                Intent i = new Intent(this.getActivity(), ProvinceActivity.class);
                startActivityForResult(i, 100);
                sellStep1.complete();
                break;
            case R.id.btn_sf2:
                sellStep2.complete();
                break;
            case R.id.btn_sc:
                sellStep3.complete();
                break;
            case R.id.btn_cx:
                sellStep4.complete();
                break;
            case R.id.btn_ry:
                sellStep6.complete();
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 130){
            btn_sf.setText(data.getStringExtra("provinceName"));
            btn_cs.setText(data.getStringExtra("marketName"));
            provinceId = data.getStringExtra("provinceId");
            marketId = data.getStringExtra("marketId");
        }





    }
}
