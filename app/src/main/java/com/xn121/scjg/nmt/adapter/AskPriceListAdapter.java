package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.bean.Product;

import java.util.List;

/**
 * Created by admin on 15/7/29.
 */
public class AskPriceListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Product> list;

    public AskPriceListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Product getItem(int i) {
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
            view = layoutInflater.inflate(R.layout.item_listview_askprice, null);
            viewHolder = new ViewHolder();
            viewHolder.query_content = (TextView)view.findViewById(R.id.query_content);
            viewHolder.expandableListView = (ExpandableListView)view.findViewById(R.id.expandable_listview);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.query_content.setText(getItem(i).getCode());


        return view;
    }


    private static class ViewHolder{
        public TextView query_content;
        public ExpandableListView expandableListView;
    }
}
