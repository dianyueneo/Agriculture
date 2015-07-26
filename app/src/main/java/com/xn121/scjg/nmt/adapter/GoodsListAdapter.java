package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.bean.Goods;

import java.util.List;

/**
 * Created by admin on 15/7/24.
 */
public class GoodsListAdapter extends BaseAdapter{

    private Context context;
    private List<Goods> list;
    private LayoutInflater layoutInflater;

    public GoodsListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<Goods> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Goods getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.show = (TextView)view.findViewById(R.id.show);
            viewHolder.hide = (TextView)view.findViewById(R.id.hide);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.show.setText(getItem(i).getName());
        viewHolder.hide.setText(getItem(i).getPinyin());

        return view;
    }

    private static class ViewHolder{
        public TextView show;
        public TextView hide;
    }
}
