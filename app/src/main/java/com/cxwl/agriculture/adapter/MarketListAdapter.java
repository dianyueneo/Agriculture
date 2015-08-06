package com.cxwl.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cxwl.agriculture.R;
import com.cxwl.agriculture.bean.Market;

import java.util.List;

/**
 * Created by hongge on 15/7/23.
 */
public class MarketListAdapter extends BaseAdapter{


    private List<Market> list;
    private LayoutInflater inflater;
    private Context context;

    public MarketListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<Market> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Market getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;


        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.show = (TextView)convertView.findViewById(R.id.show);
            viewHolder.hide = (TextView)convertView.findViewById(R.id.hide);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.show.setText(getItem(position).getMarketName());
        viewHolder.hide.setText(getItem(position).getMarketId());

        return convertView;
    }

    private static class ViewHolder{
        public TextView show;
        public TextView hide;
    }
}
