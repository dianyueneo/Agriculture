package com.xn121.scjg.nmt.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class HomeSellFragment extends Fragment{

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

    private int unSelect = 0;//未选择
    private int select = 1;//选中
    private int completed = 2;//完成

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

        //step2
        progress2 = (ImageView)rootView.findViewById(R.id.progress2);
        zhongdian = (TextView)rootView.findViewById(R.id.zhongdian);
        xzzd = (TextView)rootView.findViewById(R.id.xzzd);
        btn_sf2 = (TextView)rootView.findViewById(R.id.btn_sf2);

        //step3
        progress3 = (ImageView)rootView.findViewById(R.id.progress3);
        shangpin = (TextView)rootView.findViewById(R.id.shangpin);
        xzcp = (TextView)rootView.findViewById(R.id.xzcp);
        btn_sc = (TextView)rootView.findViewById(R.id.btn_sc);
        btn_cp = (TextView)rootView.findViewById(R.id.btn_cp);

        //step4
        progress4 = (ImageView)rootView.findViewById(R.id.progress4);
        dangdijiage = (TextView)rootView.findViewById(R.id.dangdijiage);
        btn_cx = (TextView)rootView.findViewById(R.id.btn_cx);
        tv_pf_c = (TextView)rootView.findViewById(R.id.tv_pf_c);
        tv_ls = (TextView)rootView.findViewById(R.id.tv_ls);
        tv_ls_c = (TextView)rootView.findViewById(R.id.tv_ls_c);

        //step5
        progress5 = (ImageView)rootView.findViewById(R.id.progress5);
        spcbsl = (TextView)rootView.findViewById(R.id.spcbsl);
        xiaoshou = (TextView)rootView.findViewById(R.id.xiaoshou);
        xiaoshou_cb = (TextView)rootView.findViewById(R.id.xiaoshou_cb);

        //step6
        progress6 = (ImageView)rootView.findViewById(R.id.progress6);
        yunshu = (TextView)rootView.findViewById(R.id.yunshu);
        btn_cl = (TextView)rootView.findViewById(R.id.btn_cl);
        btn_ry = (TextView)rootView.findViewById(R.id.btn_ry);

        //step7
        progress7 = (ImageView)rootView.findViewById(R.id.progress7);
        qita = (TextView)rootView.findViewById(R.id.qita);
        gongren = (TextView)rootView.findViewById(R.id.gongren);
        yuan1 = (TextView)rootView.findViewById(R.id.yuan1);
        cailiao = (TextView)rootView.findViewById(R.id.cailiao);
        yuan2 = (TextView)rootView.findViewById(R.id.yuan2);
        qtfy = (TextView)rootView.findViewById(R.id.qtfy);
        yuan3 = (TextView)rootView.findViewById(R.id.yuan3);

        xs_sl = (EditText)rootView.findViewById(R.id.xs_sl);
        xs_cb = (EditText)rootView.findViewById(R.id.xs_cb);
        et_gr = (EditText)rootView.findViewById(R.id.et_gr);
        et_cl = (EditText)rootView.findViewById(R.id.et_cl);
        et_qt = (EditText)rootView.findViewById(R.id.et_qt);

    }

    private void setStep1(int status){
        switch (status){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }

    }

    private void setStatus0(Object[] widgets){
        for(Object obj: widgets){
            if(obj instanceof ImageView){
                ((ImageView) obj).setBackgroundResource(R.drawable.progress_gray);
            }else if(obj instanceof TextView){
                ((TextView) obj).setTextColor(this.getResources().getColor(R.color.grey));
            }
        }

    }

    private void setStatus1(Object[] widgets){
        for(Object obj: widgets){
            if(obj instanceof ImageView){
                ((ImageView) obj).setBackgroundResource(R.drawable.progress_red);
            }else if(obj instanceof TextView){
                ((TextView) obj).setTextColor(this.getResources().getColor(R.color.red));
            }
        }

    }

    private void setStatus2(Object[] widgets){
        for(Object obj: widgets){
            if(obj instanceof ImageView){
                ((ImageView) obj).setBackgroundResource(R.drawable.progress_green);
            }else if(obj instanceof TextView){
                ((TextView) obj).setTextColor(this.getResources().getColor(R.color.material_blue_500));
            }
        }

    }
}
