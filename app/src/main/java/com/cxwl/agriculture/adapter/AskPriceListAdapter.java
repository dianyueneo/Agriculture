package com.cxwl.agriculture.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxwl.agriculture.Map4AskPriceActivity;
import com.cxwl.agriculture.R;
import com.cxwl.agriculture.bean.Price;
import com.cxwl.agriculture.bean.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15/7/29.
 */
public class AskPriceListAdapter extends BaseExpandableListAdapter{

    private static final int TYPE_GROUP_TEXT_QUERY = 0;
    private static final int TYPE_GROUP_IMG = 1;
    private static final int TYPE_GROUP_TEXT_MAP = 2;

    private static final int TYPE_CHILD_TITLE = 0;
    private static final int TYPE_CHILD_ITEM = 1;

    private List<Integer> typeList_group = new ArrayList<Integer>();;
    private List<Integer> typeList_child = new ArrayList<Integer>();;

    private List<Product> productList;


    private Context context;
    private LayoutInflater layoutInflater;

    public AskPriceListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;

        typeList_group.clear();
        typeList_child.clear();

        for(Product product: productList){
            typeList_group.add(TYPE_GROUP_TEXT_QUERY);
            typeList_group.add(TYPE_GROUP_IMG);
            typeList_group.add(TYPE_GROUP_TEXT_MAP);

            if(product.getList().size() > 0){
                typeList_child.add(TYPE_CHILD_TITLE);
                for(Price price:product.getList()){
                    typeList_child.add(TYPE_CHILD_ITEM);
                }
            }

        }

        notifyDataSetChanged();
    }



    @Override
    public int getGroupCount() {
        return typeList_group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition%3==1 ? (productList.get((groupPosition-1)/3)).getList().size() + 1: 0;
    }

    @Override
    public Product getGroup(int groupPosition) {
        return productList.get(groupPosition/3);
    }

    @Override
    public Product getChild(int groupPosition, int childPosition) {
        return groupPosition%3==1 ? productList.get((groupPosition-1)/3) : null;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if(view == null){
            groupViewHolder = new GroupViewHolder();
            switch (getGroupType(groupPosition)){
                case TYPE_GROUP_TEXT_QUERY:
                    view = layoutInflater.inflate(R.layout.item_listview_askprice_query, null);
                    groupViewHolder.query = (TextView)view.findViewById(R.id.query_content);
                    break;
                case TYPE_GROUP_IMG:
                    view = layoutInflater.inflate(R.layout.item_listview_askprice_result, null);
                    groupViewHolder.result = (TextView)view.findViewById(R.id.response);
                    groupViewHolder.guide = (ImageView)view.findViewById(R.id.guide);
                    break;
                case TYPE_GROUP_TEXT_MAP:
                    view = layoutInflater.inflate(R.layout.item_listview_askprice_map, null);
                    break;
                default:
                    break;
            }
            view.setTag(groupViewHolder);

        }else{
            groupViewHolder = (GroupViewHolder)view.getTag();
        }

        final Product product = getGroup(groupPosition);

        switch (getGroupType(groupPosition)){
            case TYPE_GROUP_TEXT_QUERY:
                groupViewHolder.query.setText(product.getQuestion());
                break;
            case TYPE_GROUP_IMG:
                groupViewHolder.result.setText(product.getTitle());
                if(isExpanded){
                    groupViewHolder.guide.setImageResource(R.drawable.guide_down);
                }else{
                    groupViewHolder.guide.setImageResource(R.drawable.guide_right);
                }
                break;
            case TYPE_GROUP_TEXT_MAP:
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Map4AskPriceActivity.class);
                        intent.putParcelableArrayListExtra("list", (ArrayList<Price>)product.getList());
                        context.startActivity(intent);
                        Log.i("test", product.getProduct());
                    }
                });
                break;
            default:
                break;
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;

        if(view == null){
            childViewHolder = new ChildViewHolder();
            switch (getChildType(groupPosition,childPosition)){
                case TYPE_CHILD_TITLE:
                    view = layoutInflater.inflate(R.layout.item_listview_askprice_result_title, null);
                    childViewHolder.qgpfj = (TextView)view.findViewById(R.id.qgpfj);
                    childViewHolder.lsj = (TextView)view.findViewById(R.id.lsj);
                    break;
                case TYPE_CHILD_ITEM:
                    view = layoutInflater.inflate(R.layout.item_listview_askprice_result_item, null);
                    childViewHolder.num = (TextView)view.findViewById(R.id.num);
                    childViewHolder.product = (TextView)view.findViewById(R.id.product);
                    childViewHolder.name = (TextView)view.findViewById(R.id.name);
                    childViewHolder.address = (TextView)view.findViewById(R.id.address);
                    childViewHolder.pfj_content = (TextView)view.findViewById(R.id.pfj_content);
                    childViewHolder.time_content = (TextView)view.findViewById(R.id.time_content);
                    break;
                default:
                    break;
            }
            view.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder)view.getTag();
        }

        Product product = getChild(groupPosition, childPosition);

        switch (getChildType(groupPosition,childPosition)){
            case TYPE_CHILD_TITLE:
                childViewHolder.qgpfj.setText(product.getAp());
                childViewHolder.lsj.setText(product.getRrp());
                break;
            case TYPE_CHILD_ITEM:
                childViewHolder.num.setText(""+childPosition);
                Price price = product.getList().get(childPosition - 1);
                childViewHolder.product.setText(price.getProduct());
                childViewHolder.name.setText(price.getName());
                childViewHolder.address.setText(price.getAddress());
                childViewHolder.pfj_content.setText(price.getPrice());
                childViewHolder.time_content.setText(price.getDate());
                break;
            default:
                break;
        }


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }



    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return childPosition == 0 ? TYPE_CHILD_TITLE : TYPE_CHILD_ITEM;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return typeList_group.get(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return 3;
    }


    private static class GroupViewHolder{
        public TextView query;
        public TextView result;
        public ImageView guide;
    }

    private static class ChildViewHolder{
        public TextView qgpfj;
        public TextView lsj;

        public TextView num;
        public TextView product;
        public TextView name;
        public TextView address;
        public TextView pfj_content;
        public TextView time_content;


    }

}
