package com.cxwl.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.cxwl.agriculture.R;

import java.util.List;

import cn.com.weather.api.WeatherAPI;
import cn.com.weather.constants.Constants;

/**
 * Created by admin on 15/8/4.
 */
public class WeatherListAdapter extends BaseAdapter{

    private Context context;
    private List<String> codes;
    private LayoutInflater layoutInflater;

    public WeatherListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return codes.size();
    }

    @Override
    public String getItem(int i) {
        return codes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_gridview_weather, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.iv_weather);
            viewHolder.textView = (TextView)view.findViewById(R.id.tv_weather);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        String code = getItem(i);

        if(!"-1".equals(code)){
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.textView.setVisibility(View.VISIBLE);

            int resId = context.getResources().getIdentifier("day"+code+"_mini", "drawable", context.getPackageName());
            viewHolder.imageView.setImageResource(resId);
            String num = String.valueOf(Integer.parseInt(code));
            viewHolder.textView.setText(WeatherAPI.parseWeather(context, num, Constants.Language.ZH_CN));
        }else{
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.textView.setVisibility(View.INVISIBLE);
        }

        return view;
    }


    private static class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
