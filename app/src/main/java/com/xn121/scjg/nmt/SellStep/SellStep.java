package com.xn121.scjg.nmt.SellStep;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;

/**
 * Created by hongge on 15/7/22.
 */
public class SellStep extends AbstractHandler implements Handler{

    private int status;
    private Object[] widgets;
    private Context context;

    public static final int UNSELECT = 0;//未选择
    public static final int SELECT = 1;//选中
    public static final int COMPLETED = 2;//完成

    public SellStep(Context context, Object[] widgets, int status) {
        this.status = status;
        this.widgets = widgets;
        this.context = context;

        setStatus(status);
    }

    @Override
    public void complete() {
        if(status == UNSELECT){
            status = SELECT;
            setStatus(status);
        }else if(status == SELECT){
            status = COMPLETED;
            setStatus(status);
            if(getHandler() != null){
                getHandler().complete();
            }
        }
    }

    private void setStatus(int status){
        int resid = R.drawable.progress_gray;
        int colorid = R.color.grey;
        boolean clickable = false;
        boolean focusable = false;

        switch (status){
            case UNSELECT:
                resid = R.drawable.progress_gray;
                colorid = R.color.grey;
                clickable = false;
                focusable = false;
                break;
            case SELECT:
                resid = R.drawable.progress_red;
                colorid = R.color.red;
                clickable = true;
                focusable = true;
                break;
            case COMPLETED:
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
                ((ImageView) obj).setImageResource(resid);
            }else if(obj.getClass().equals(AppCompatTextView.class) || obj.getClass().equals(TextView.class)){
                TextView tv = ((TextView) obj);
                if(tv.getBackground() == null){
                    tv.setTextColor(context.getResources().getColor(colorid));
                }else {
                    tv.setBackgroundColor(context.getResources().getColor(colorid));
                }
                tv.setClickable(clickable);

            }else if(obj.getClass().equals(AppCompatEditText.class) || obj.getClass().equals(EditText.class)){
//                ((EditText)obj).setFocusable(focusable);
                ((EditText)obj).setEnabled(focusable);
            }
        }

    }
}
