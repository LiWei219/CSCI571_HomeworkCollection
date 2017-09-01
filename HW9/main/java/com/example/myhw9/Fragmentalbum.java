package com.example.myhw9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/23.
 */

public class Fragmentalbum extends Fragment {

    public ExpandableListView lv;
    public View rl;
    public TextView noalbum;
    private String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler;
    public LinkedList<String> header;
    public HashMap<String,List<String>> map;
    public JSONArray albdata;
    public StringBuilder res;
    private ExpandableListAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rl =  inflater.inflate(R.layout.detail_album, container, false);
        return rl;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        lv = (ExpandableListView)getActivity().findViewById(R.id.detailist) ;
        noalbum = (TextView)getActivity().findViewById(R.id.noalbum);
        noalbum.setVisibility(rl.GONE);
        Getdetail();
    }

    public void Getdetail(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://sample-env.dinpjbwdem.us-west-2.elasticbeanstalk.com").newBuilder();
        urlBuilder.addQueryParameter("search", "detail");
        urlBuilder.addQueryParameter("id", detail.id);
        String path = urlBuilder.build().toString();
        Log.e(TAG,"path:"+path);
        fetchinfo(path);
    }

    public void fetchinfo(String path){
        res = new StringBuilder("");
        header = new LinkedList<String>();
        map = new HashMap<String,List<String>>();

        OkHttpClient client = new OkHttpClient.Builder().readTimeout(8, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().url(path).build();
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e(TAG, "Detail request failed.");
            }

            public void onResponse(Call call,final Response response) throws IOException {
                Log.e(TAG, "Detail request success.");

                if (!response.isSuccessful()) {
                    try {
                        throw new IOException("Unexpected code " + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                res = new StringBuilder(response.body().string());
                                String test = "got it";
                                if (res != null) {
                                    try {
                                        JSONObject info = new JSONObject(res.toString());
                                        if(info.optJSONObject("albums")!=null){
                                            albdata = info.optJSONObject("albums").getJSONArray("data");
                                            if(albdata!=null){
                                                for(int i = 0; i < albdata.length(); i++){
                                                    JSONObject dataobj = albdata.getJSONObject(i);
                                                    header.add(dataobj.optString("name"));
                                                    if(dataobj.optJSONObject("photos")!=null){
                                                        JSONArray imgarray = dataobj.optJSONObject("photos").optJSONArray("data");
                                                        if(imgarray!=null){
                                                            LinkedList<String> imglist = new LinkedList<String>();
                                                            for(int j = 0; j < imgarray.length();j++){
                                                                imglist.add(imgarray.optJSONObject(j).optString("picture"));
                                                            }
                                                            map.put(dataobj.optString("name"),imglist);
                                                        }
                                                    }else{
                                                        LinkedList<String> imglist = new LinkedList<String>();
                                                        map.put(dataobj.optString("name"),imglist);
                                                    }
                                                }
                                            }else{
                                                noalbum.setVisibility(rl.VISIBLE);
                                            }
                                        }else{
                                            noalbum.setVisibility(rl.VISIBLE);
                                        }
                                        mAdapter = new ExpandableListAdapter(detail.dcontext,header,map);
                                        lv.setAdapter(mAdapter);
                                    }catch (JSONException e){
                                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                                    }
                                }else{
                                    Log.e(TAG, "Couldn't get json from server.");
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }
}
