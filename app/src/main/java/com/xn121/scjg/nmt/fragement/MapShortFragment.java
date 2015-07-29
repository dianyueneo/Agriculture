package com.xn121.scjg.nmt.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xn121.scjg.nmt.MapActivity;
import com.xn121.scjg.nmt.MyApplication;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.adapter.MapListAdapter;
import com.xn121.scjg.nmt.bean.Profit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hongge on 15/7/25.
 */
public class MapShortFragment extends Fragment implements AdapterView.OnItemClickListener{

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

        List<Profit> list = ((MyApplication)getActivity().getApplication()).getProfitList();

        if(list != null && list.size() > 0){
            list = srotByPath(list);
            mapListAdapter.setList(list);
            listView.setAdapter(mapListAdapter);
            listView.setOnItemClickListener(this);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Profit profit = mapListAdapter.getItem(i);
        ((MapActivity)getActivity()).startDriveRouteQuery(profit);
    }

    private List<Profit> srotByPath(List<Profit> profits){
        List<Profit> list = profits;
        Collections.sort(list, new SortByPath());
        return list;
    };

    class SortByPath implements Comparator<Profit> {
        @Override
        public int compare(Profit profit, Profit t1) {
            return Double.compare(t1.getDistance(), profit.getDistance());
        }
    }
}
