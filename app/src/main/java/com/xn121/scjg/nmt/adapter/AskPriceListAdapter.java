package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.xn121.scjg.nmt.bean.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15/7/29.
 */
public class AskPriceListAdapter extends BaseExpandableListAdapter{

//    private static final int TYPE_GROUP_LIST = 0;
//    private static final int TYPE_GROUP_MAP = 1;

    private static final int TYPE_CHILD_TITLE = 2;
    private static final int TYPE_CHILD_ITEM = 3;

    private List<Integer> typeList;

    private List<Product> productList;


    private Context context;
    private LayoutInflater layoutInflater;

    public AskPriceListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;

        if(productList.size() > 0){
            typeList.add(TYPE_CHILD_TITLE);
            for(Product product: productList){
                typeList.add(TYPE_CHILD_ITEM);
            }
        }

        notifyDataSetChanged();
    }



    @Override
    public int getGroupCount() {
        return productList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (productList.get(groupPosition/2)).getList().size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return productList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return typeList.get(groupPosition) == 0 ? (productList.get(groupPosition/2)).getList().get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }



    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return typeList.get(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return typeList.size();
    }

}
