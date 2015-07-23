package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.bean.Province;

import java.util.List;

/**
 * Created by admin on 15/7/23.
 */
public class ProvinceListAdapter extends BaseAdapter{

    private List<Province> list;
    private LayoutInflater inflater;

    public ProvinceListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Province getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = inflater.inflate(R.layout.listview_item, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_show = (TextView)view.findViewById(R.id.show);
            viewHolder.tv_hide = (TextView)view.findViewById(R.id.hide);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.tv_show.setText(getItem(i).getProvinceName());
        viewHolder.tv_hide.setText(getItem(i).getProvinceId());

        return view;
    }

    public void setList(List<Province> list){
        this.list = list;
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        TextView tv_show;
        TextView tv_hide;
    }

}
