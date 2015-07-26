package com.xn121.scjg.nmt.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.adapter.MapListAdapter;
import com.xn121.scjg.nmt.bean.Profit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongge on 15/7/25.
 */
public class MapProfitFragment extends Fragment{

    private View rootView;
    private ListView listView;
    private MapListAdapter mapListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_map, null);
            init();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init(){
        listView = (ListView)rootView.findViewById(R.id.listview);
        mapListAdapter = new MapListAdapter(getActivity());

        List<Profit> list = new ArrayList<Profit>();
        list.add(new Profit("北京大红门农副产品批发市场","北京顺鑫石门农副产品批发市场",10700.00));
        list.add(new Profit("北京大红门农副产品批发市场","北京顺鑫石门农副产品批发市场",10700.00));
        list.add(new Profit("北京大红门农副产品批发市场","北京顺鑫石门农副产品批发市场",10700.00));

        mapListAdapter.setList(list);
        listView.setAdapter(mapListAdapter);
    }
}
