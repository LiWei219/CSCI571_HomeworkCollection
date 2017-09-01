package com.example.myhw9;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Favuser extends Fragment {
    public ListView lv;
    public View rl;
    private String TAG = MainActivity.class.getSimpleName();
    public LinkedList<obj> array;
    public JSONArray data;
    public StringBuilder res;
    private MyAdapter mAdapter = null;
    private SPUtils sp;
    private Map<String,?> spmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rl =  inflater.inflate(R.layout.result_user, container, false);
        spmap = sp.getAll(result.mcontext);
        return rl;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView)getActivity().findViewById(R.id.result_user) ;
        getActivity().findViewById(R.id.userbtn).setVisibility(View.GONE);
        GetItem();
    }

    public void GetItem(){
        array = new LinkedList<obj>();
        for(Map.Entry<String,?> entry: spmap.entrySet()){
            String temp = (String)entry.getValue();
      //      Log.e(TAG,"spmap key:"+entry.getKey()+"  spmap value:"+temp);
            String[] it = temp.split("#");
      /*      for(int i = 0; i < it.length; i++){
                Log.e(TAG,it[i]);
            }   */
            if(it[1].equals("user")){
                obj object = new obj(it[0],it[2],entry.getKey());
                array.add(object);
            }
        }
        mAdapter = new MyAdapter(array,getContext(),"user");
        lv.setAdapter(mAdapter);
    }
}
