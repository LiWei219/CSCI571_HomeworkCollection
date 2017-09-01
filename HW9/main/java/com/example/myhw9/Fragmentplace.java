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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
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

public class Fragmentplace extends Fragment{
    public ListView lv;
    public View rl;
    private String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler;
    public LinkedList<obj> array;
    public StringBuilder prev;
    public StringBuilder next;
    public JSONArray data;
    public StringBuilder res;
    private MyAdapter mAdapter = null;
    public Button previous;
    public Button nex;
    public double lon;
    public double lat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rl =  inflater.inflate(R.layout.result_place, container, false);
        return rl;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        lv = (ListView)getActivity().findViewById(R.id.result_place) ;
        previous =(Button) getActivity().findViewById(R.id.placeprev);
        nex = (Button) getActivity().findViewById(R.id.placenext);
        GetItem();
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchinfo(prev.toString());
            }
        });
        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchinfo(next.toString());
            }
        });
        GPSTracker tracker = new GPSTracker(result.mcontext);
        if(!tracker.canGetLocation()){
            tracker.showSettingsAlert();
        }else{
            lat = tracker.getLatitude();
            lon = tracker.getLongitude();
        }

    }

    public void GetItem() {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://sample-env.dinpjbwdem.us-west-2.elasticbeanstalk.com").newBuilder();
        urlBuilder.addQueryParameter("search", "info");
        urlBuilder.addQueryParameter("key", MainActivity.key);
        urlBuilder.addQueryParameter("type", "place");
        urlBuilder.addQueryParameter("lat",String.valueOf(lat));
        urlBuilder.addQueryParameter("lon",String.valueOf(lon));
        String path = urlBuilder.build().toString();

        fetchinfo(path);
    }

    public void fetchinfo(String path){
        array = new LinkedList<obj>();
        prev = new StringBuilder("");
        next = new StringBuilder("");
        res = new StringBuilder("");

        if(path==null || path.equals("")){
            mAdapter = new MyAdapter(array,result.mcontext,"event");
            lv.setAdapter(mAdapter);
        }else {
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(8, TimeUnit.SECONDS).build();
            final Request request = new Request.Builder().url(path).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //失败
                    Log.e(TAG, "Http request failed.");
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    Log.e(TAG, "Http request success.");

                    if (!response.isSuccessful()) {
                        try {
                            throw new IOException("Unexpected code " + response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {

                        // do something wih the result
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    res = new StringBuilder(response.body().string());
                                    String test = "got it";

                                    if (res != null) {
                                        try {
                                            JSONObject info = new JSONObject(res.toString());
                                            data = info.optJSONArray("data");
                                            JSONObject page = info.optJSONObject("paging");
                                            if (page != null) {
                                                Log.e(TAG, "previous=" + page.optString("previous"));
                                                if (page.optString("previous").equals("") || (page.optString("previous") == null)) {
                                                    //set prev unabled
                                                    prev = new StringBuilder("");
                                                    Log.e(TAG, "pre==null");
                                                    previous.setEnabled(false);
                                                } else {
                                                    prev = new StringBuilder(page.optString("previous"));
                                                    Log.e(TAG, prev.toString());
                                                    previous.setEnabled(true);
                                                }

                                                if (page.optString("next").equals("") || (page.optString("next") == null)) {
                                                    //set next unabled
                                                    next = new StringBuilder("");
                                                    Log.e(TAG, "next==null");
                                                    nex.setEnabled(false);
                                                } else {
                                                    next = new StringBuilder(page.optString("next"));
                                                    Log.e(TAG, next.toString());
                                                    nex.setEnabled(true);
                                                }
                                            } else {
                                                prev = new StringBuilder("");
                                                Log.e(TAG, "pre==null");
                                                previous.setEnabled(false);
                                                next = new StringBuilder("");
                                                Log.e(TAG, "next==null");
                                                nex.setEnabled(false);
                                            }
                                            if (data != null) {
                                                for (int i = 0; i < data.length(); i++) {
                                                    JSONObject dataobj = data.getJSONObject(i);
                                                    JSONObject picture = dataobj.optJSONObject("picture");
                                                    obj object = new obj(dataobj.optString("name"), picture.optJSONObject("data").optString("url"), dataobj.optString("id"));
                                                    array.add(object);
                                                }
                                            }
                                            mAdapter = new MyAdapter(array, result.mcontext, "place");
                                            lv.setAdapter(mAdapter);

                                        } catch (final JSONException e) {
                                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                                        }
                                    } else {
                                        Log.e(TAG, "Couldn't get json from server.");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
