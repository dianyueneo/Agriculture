package com.xn121.scjg.nmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xn121.scjg.nmt.adapter.ProvinceListAdapter;
import com.xn121.scjg.nmt.bean.Province;
import com.xn121.scjg.nmt.db.AssetsUtil;

/**
 * Created by admin on 15/7/23.
 */
public class ProvinceActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ProvinceListAdapter adapter;
    private Province province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);

        listView = (ListView)this.findViewById(R.id.list);

        adapter = new ProvinceListAdapter(this);
        adapter.setList(AssetsUtil.getProvinceList(this));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        province = adapter.getItem(position);
        Intent intent = new Intent(ProvinceActivity.this, MarketActivity.class);
        intent.putExtra("provinceId", province.getProvinceId());
        startActivityForResult(intent, 110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 110 && resultCode ==120){
            Intent intent = new Intent();
            intent.putExtra("provinceId", province.getProvinceId());
            intent.putExtra("provinceName", province.getProvinceName());
            intent.putExtra("marketId", data.getStringExtra("marketId"));
            intent.putExtra("marketName", data.getStringExtra("marketName"));
            setResult(130, intent);
            finish();
        }
    }
}
