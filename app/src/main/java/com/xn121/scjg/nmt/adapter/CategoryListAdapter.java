package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xn121.scjg.nmt.R;

import java.util.List;

/**
 * Created by admin on 15/7/24.
 */
public class CategoryListAdapter extends BaseAdapter{

    private List<String> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public CategoryListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
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
            view = layoutInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.show = (TextView)view.findViewById(R.id.show);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.show.setText(getItem(i));

        return view;
    }

    private static class ViewHolder{
        public TextView show;
    }
}
