package com.xn121.scjg.nmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xn121.scjg.nmt.adapter.CategoryListAdapter;
import com.xn121.scjg.nmt.fragement.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15/7/24.
 */
public class CategoryActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private CategoryListAdapter goodsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);

        listView = (ListView)this.findViewById(R.id.list);

        goodsListAdapter = new CategoryListAdapter(this);
        List<String> list = new ArrayList<String>();
        list.add("粮食");
        list.add("蔬菜");
        list.add("水果");
        list.add("肉类");
        list.add("鱼类");
        list.add("干果");
        list.add("油类");
        list.add("茶叶");
        list.add("药材");
        goodsListAdapter.setList(list);

        listView.setAdapter(goodsListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String category = goodsListAdapter.getItem(i);

        Intent intent = new Intent(CategoryActivity.this, GoodsActivity.class);
        intent.putExtra("category",category);
        startActivityForResult(intent, HomeFragment.REQUEST_CODE_CATEGORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HomeFragment.REQUEST_CODE_CATEGORY && resultCode == HomeFragment.RESULT_CODE_GOODS){
            setResult(HomeFragment.RESULT_CODE_CATEGORY, data);
            finish();
        }
    }
}
