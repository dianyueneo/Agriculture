package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.bean.Profit;

import java.util.List;

/**
 * Created by hongge on 15/7/26.
 */
public class MapListAdapter extends BaseAdapter {

    private List<Profit> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MapListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<Profit> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Profit getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_listview_map, null);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView)convertView.findViewById(R.id.number);
            viewHolder.market_qidian = (TextView)convertView.findViewById(R.id.market_qidian);
            viewHolder.market_zhongdian = (TextView)convertView.findViewById(R.id.market_zhongdian);
            viewHolder.profit = (TextView)convertView.findViewById(R.id.profit);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.number.setText(position+1+"");
        viewHolder.market_qidian.setText(getItem(position).getStart());
        viewHolder.market_zhongdian.setText(getItem(position).getEnd());
        viewHolder.profit.setText(""+getItem(position).getProfit());

        return convertView;
    }

    private static class ViewHolder{
        public TextView number;
        public TextView market_qidian;
        public TextView market_zhongdian;
        public TextView profit;
    }
}
