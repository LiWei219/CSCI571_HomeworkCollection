package com.example.myhw9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

public class Fragmentpost extends Fragment {
    public ListView lv;
    public View rl;
    public TextView nopost;
    private String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler;
    public LinkedList<postitem> posts;
    public JSONArray postdata;
    public StringBuilder res;
    private PostListAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rl =  inflater.inflate(R.layout.detail_post, container, false);
        return rl;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        lv = (ListView)getActivity().findViewById(R.id.postlist) ;
        nopost = (TextView)getActivity().findViewById(R.id.nopost);
        nopost.setVisibility(rl.GONE);
        GetPosts();
    }

    public void GetPosts(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://sample-env.dinpjbwdem.us-west-2.elasticbeanstalk.com").newBuilder();
        urlBuilder.addQueryParameter("search", "detail");
        urlBuilder.addQueryParameter("id", detail.id);
        String path = urlBuilder.build().toString();
        Log.e(TAG,"path:"+path);
        fetchinfo(path);
    }

    public void fetchinfo(String path){
        posts = new LinkedList<postitem>();
        res = new StringBuilder("");

        OkHttpClient client = new OkHttpClient.Builder().readTimeout(8, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().url(path).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e(TAG, "Posts request failed.");
            }
            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                Log.e(TAG, "Posts request success.");
                if (!response.isSuccessful()) {
                    try {
                        throw new IOException("Unexpected code " + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                res = new StringBuilder(response.body().string());
                                String test = "got it";
                                if (res != null) {
                                    try {
                                        JSONObject info = new JSONObject(res.toString());
                                        if(info.optJSONObject("posts")!=null){
                                            postdata = info.optJSONObject("posts").getJSONArray("data");
                                            if(postdata!=null){
                                                for(int i = 0; i < postdata.length(); i++){
                                                    JSONObject dataobj = postdata.getJSONObject(i);
                                                    if(dataobj!=null){
                                                        String time = "";
                                                        time += dataobj.optString("created_time").substring(0,10);
                                                        time = time +" ";
                                                        time += dataobj.optString("created_time").substring(11,19);
                                                        postitem item = new postitem(info.getString("name"),info.optJSONObject("picture").optJSONObject("data").getString("url"),
                                                                time,dataobj.optString("message"),dataobj.optString("story"));
                                                        posts.add(item);
                                                    }else{
                                                        nopost.setVisibility(rl.VISIBLE);
                                                    }
                                                }
                                                mAdapter = new PostListAdapter(posts,detail.dcontext);
                                                lv.setAdapter(mAdapter);
                                            }else{
                                                nopost.setVisibility(rl.VISIBLE);
                                            }
                                        }else{
                                            nopost.setVisibility(rl.VISIBLE);
                                        }
                                    }catch (final JSONException e) {
                                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                                    }
                                }else{
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
