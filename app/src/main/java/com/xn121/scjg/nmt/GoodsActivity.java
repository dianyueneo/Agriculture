package com.xn121.scjg.nmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xn121.scjg.nmt.adapter.GoodsListAdapter;
import com.xn121.scjg.nmt.bean.Goods;
import com.xn121.scjg.nmt.db.AssetsUtil;
import com.xn121.scjg.nmt.fragement.HomeFragment;

/**
 * Created by admin on 15/7/24.
 */
public class GoodsActivity  extends Activity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private GoodsListAdapter goodsListAdapter;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);

        listView = (ListView)this.findViewById(R.id.list);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        goodsListAdapter = new GoodsListAdapter(this);
        goodsListAdapter.setList(AssetsUtil.getGoodsList(this, category));
        listView.setAdapter(goodsListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Goods goods = goodsListAdapter.getItem(i);

        Intent intent = new Intent();
        intent.putExtra("category", category);
        intent.putExtra("pinyin", goods.getPinyin());
        intent.putExtra("name", goods.getName());
        setResult(HomeFragment.RESULT_CODE_GOODS, intent);
        finish();
    }
}
