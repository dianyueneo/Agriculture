package com.xn121.scjg.nmt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.xn121.scjg.nmt.adapter.ListAdapter;
import com.xn121.scjg.nmt.db.AssetsUtil;

/**
 * Created by admin on 15/7/23.
 */
public class ProvinceActivity extends Activity{
    private ListView listView;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setContentView(R.layout.activity_list);

        listView = (ListView)this.findViewById(R.id.list);

        adapter = new ListAdapter(this);
        adapter.setList(AssetsUtil.getProvinceList(this));

        listView.setAdapter(adapter);

    }

}
