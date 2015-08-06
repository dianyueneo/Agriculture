package com.cxwl.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cxwl.agriculture.adapter.ProvinceListAdapter;
import com.cxwl.agriculture.bean.Province;
import com.cxwl.agriculture.db.AssetsUtil;
import com.cxwl.agriculture.fragement.HomeFragment;

/**
 * Created by admin on 15/7/23.
 */
public class ProvinceActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ProvinceListAdapter adapter;
    private Province province;

    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);

        listView = (ListView)this.findViewById(R.id.list);


        Intent intent = this.getIntent();
        requestCode = intent.getIntExtra("requestCode", 0);

        adapter = new ProvinceListAdapter(this);
        adapter.setList(AssetsUtil.getProvinceList(this));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        province = adapter.getItem(position);

        if(requestCode == HomeFragment.REQUEST_CODE_PROVINCESTART){
            Intent intent = new Intent(ProvinceActivity.this, MarketActivity.class);
            intent.putExtra("provinceId", province.getProvinceId());
            startActivityForResult(intent, HomeFragment.REQUEST_CODE_PROVINCE);
        }else if(requestCode == HomeFragment.REQUEST_CODE_PROVINCEEND){
            Intent intent = new Intent();
            intent.putExtra("provinceId", province.getProvinceId());
            intent.putExtra("provinceName", province.getProvinceName());
            setResult(HomeFragment.RESULT_CODE_PROVINCE, intent);
            finish();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HomeFragment.REQUEST_CODE_PROVINCE && resultCode == HomeFragment.RESULT_CODE_MARKET){
            Intent intent = new Intent();
            intent.putExtra("provinceId", province.getProvinceId());
            intent.putExtra("provinceName", province.getProvinceName());
            intent.putExtra("marketId", data.getStringExtra("marketId"));
            intent.putExtra("marketName", data.getStringExtra("marketName"));
            setResult(HomeFragment.RESULT_CODE_PROVINCE, intent);
            finish();
        }
    }
}
