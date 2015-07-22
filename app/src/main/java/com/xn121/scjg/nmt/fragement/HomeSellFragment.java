package com.xn121.scjg.nmt.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;

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

    private int unSelect = 0;//未选择
    private int select = 1;//选中
    private int completed = 2;//完成

    private Object[] step1 = null;
    private Object[] step2 = null;
    private Object[] step3 = null;
    private Object[] step4 = null;
    private Object[] step5 = null;
    private Object[] step6 = null;
    private Object[] step7 = null;

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

        //step2
        progress2 = (ImageView)rootView.findViewById(R.id.progress2);
        zhongdian = (TextView)rootView.findViewById(R.id.zhongdian);
        xzzd = (TextView)rootView.findViewById(R.id.xzzd);
        btn_sf2 = (TextView)rootView.findViewById(R.id.btn_sf2);

        btn_sf2.setOnClickListener(this);

        step2 = new Object[]{progress2, zhongdian, xzzd, btn_sf2};

        //step3
        progress3 = (ImageView)rootView.findViewById(R.id.progress3);
        shangpin = (TextView)rootView.findViewById(R.id.shangpin);
        xzcp = (TextView)rootView.findViewById(R.id.xzcp);
        btn_sc = (TextView)rootView.findViewById(R.id.btn_sc);
        btn_cp = (TextView)rootView.findViewById(R.id.btn_cp);

        btn_sc.setOnClickListener(this);
        btn_cp.setOnClickListener(this);

        step3 = new Object[]{progress3, shangpin, xzcp, btn_sc, btn_cp};

        //step4
        progress4 = (ImageView)rootView.findViewById(R.id.progress4);
        dangdijiage = (TextView)rootView.findViewById(R.id.dangdijiage);
        btn_cx = (TextView)rootView.findViewById(R.id.btn_cx);
        tv_pf_c = (TextView)rootView.findViewById(R.id.tv_pf_c);
        tv_ls = (TextView)rootView.findViewById(R.id.tv_ls);
        tv_ls_c = (TextView)rootView.findViewById(R.id.tv_ls_c);

        btn_cx.setOnClickListener(this);

        step4 = new Object[]{progress4, dangdijiage, btn_cx, tv_pf_c, tv_ls, tv_ls_c};

        //step5
        progress5 = (ImageView)rootView.findViewById(R.id.progress5);
        spcbsl = (TextView)rootView.findViewById(R.id.spcbsl);
        xiaoshou = (TextView)rootView.findViewById(R.id.xiaoshou);
        xiaoshou_cb = (TextView)rootView.findViewById(R.id.xiaoshou_cb);
        xs_sl = (EditText)rootView.findViewById(R.id.xs_sl);
        xs_cb = (EditText)rootView.findViewById(R.id.xs_cb);

        step5 = new Object[]{progress5, spcbsl, xiaoshou, xiaoshou_cb, xs_sl, xs_cb};

        //step6
        progress6 = (ImageView)rootView.findViewById(R.id.progress6);
        yunshu = (TextView)rootView.findViewById(R.id.yunshu);
        btn_cl = (TextView)rootView.findViewById(R.id.btn_cl);
        btn_ry = (TextView)rootView.findViewById(R.id.btn_ry);

        btn_cl.setOnClickListener(this);
        btn_ry.setOnClickListener(this);

        step6 = new Object[]{progress6, yunshu, btn_cl, btn_ry};

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

        step7 = new Object[]{progress7, qita, gongren, yuan1, cailiao, yuan2, qtfy, yuan3, et_gr, et_cl, et_qt};

        //step8
        btn_hqxl = (TextView)rootView.findViewById(R.id.btn_hqxl);

        btn_hqxl.setOnClickListener(this);

        setStatus(step1, 1);
        setStatus(step5, 0);

    }


    private void setStatus(Object[] widgets, int status){
        int resid = R.drawable.progress_gray;
        int colorid = R.color.grey;
        boolean clickable = false;
        boolean focusable = false;

        switch (status){
            case 0:
                resid = R.drawable.progress_gray;
                colorid = R.color.grey;
                clickable = false;
                focusable = false;
                break;
            case 1:
                resid = R.drawable.progress_red;
                colorid = R.color.red;
                clickable = true;
                focusable = true;
                break;
            case 2:
                resid = R.drawable.progress_green;
                colorid = R.color.material_blue_500;
                clickable = true;
                focusable = true;
                break;
            default:
                break;
        }

        for(Object obj: widgets){
            if(ImageView.class.isInstance(obj)){
                Log.i("test2", "ImageView====" + obj);
                ((ImageView) obj).setImageResource(resid);
            }else if(TextView.class.isInstance(obj) && AppCompatTextView.class.isInstance(obj)){
                Log.i("test2", "TextView===="+obj);
                TextView tv = ((TextView) obj);
                if(tv.getBackground() == null){
                    tv.setTextColor(this.getResources().getColor(colorid));
                }else {
                    tv.setBackgroundColor(this.getResources().getColor(colorid));
                }
                tv.setClickable(clickable);

            }else if(EditText.class.isInstance(obj) && AppCompatEditText.class.isInstance(obj)){
                Log.i("test2", "EditText===="+obj);
                ((EditText)obj).setFocusable(focusable);
                ((EditText)obj).setEnabled(focusable);
            }
        }

    }

    @Override
    public void onClick(View view) {

    }
}
