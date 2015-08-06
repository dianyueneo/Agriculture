package com.cxwl.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cxwl.agriculture.adapter.MarketListAdapter;
import com.cxwl.agriculture.bean.Market;
import com.cxwl.agriculture.db.AssetsUtil;
import com.cxwl.agriculture.fragement.HomeFragment;

/**
 * Created by hongge on 15/7/23.
 */
public class MarketActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private MarketListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        listView = (ListView)this.findViewById(R.id.list);

        Intent intent = getIntent();
        String provinceId = intent.getStringExtra("provinceId");

        adapter = new MarketListAdapter(this);
        adapter.setList(AssetsUtil.getMarketList(this, provinceId));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Market market = adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("marketId", market.getMarketId());
        intent.putExtra("marketName", market.getMarketName());
        setResult(HomeFragment.RESULT_CODE_MARKET, intent);
        finish();
    }
}
