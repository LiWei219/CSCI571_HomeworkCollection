package com.example.myhw9;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<obj> mObj;
    public String type;
    private String TAG = MainActivity.class.getSimpleName();
    public final static String EXTRA_MESSAGE = "com.example.myhw9.MESSAGE";
    public SPUtils sp;
    public Map<String,?> spmap;

    public MyAdapter(){};


    public MyAdapter(LinkedList<obj> mObj, Context mContext,String type){
        this.mObj = mObj;
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public int getCount(){
        return mObj.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ViewHolder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

      //  sp.clear(MainActivity.mainContext);
        spmap = sp.getAll(MainActivity.mainContext);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.objcard,parent,false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.fb_img);
            holder.txt_content = (TextView) convertView.findViewById(R.id.fb_name);
            holder.favorite = (ImageView)convertView.findViewById(R.id.fb_fav);
            holder.detail = (ImageView)convertView.findViewById(R.id.fb_det);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(mObj.get(position).getPicurl()).fit().into(holder.img_icon);

        holder.txt_content.setText(mObj.get(position).getName());
        //judge this item in the favorite list or not
      //  if(true){
      //      holder.favorite.setImageResource(R.drawable.favorites_on);
      //  }else
        if(spmap.containsKey(mObj.get(position).getId())){
            holder.favorite.setImageResource(R.drawable.favorites_on);
        }else{
            holder.favorite.setImageResource(R.drawable.favorites_off);
        }
        holder.favorite.setTag("off");
        holder.detail.setImageResource(R.drawable.details);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout parentrow = (LinearLayout)v.getParent();
                ImageView childimg = (ImageView)parentrow.getChildAt(2);

                if(String.valueOf(childimg.getTag()).equals("off")){
                    childimg.setImageResource(R.drawable.favorites_on);
                    childimg.setTag("on");
                    String str = mObj.get(position).getName()+"#"+type+"#"+mObj.get(position).getPicurl();
                    if(!spmap.containsKey(mObj.get(position).getId())){
                        sp.put(MainActivity.mainContext,mObj.get(position).getId(),str);
                    }
                }else{
                    childimg.setImageResource(R.drawable.favorites_off);
                    childimg.setTag("off");
                    if(spmap.containsKey(mObj.get(position).getId())){
                        sp.remove(MainActivity.mainContext,mObj.get(position).getId());
                    }
                }
                parentrow.refreshDrawableState();
            }
        });

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"details function");
                String infostr = mObj.get(position).getId()+"#"+mObj.get(position).getName()+"#"+
                        type+"#"+mObj.get(position).getPicurl();
                Intent intent = new Intent(mContext, detail.class);
                intent.putExtra(EXTRA_MESSAGE, infostr);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        ImageView img_icon;
        TextView txt_content;
        ImageView favorite;
        ImageView detail;
    }

}
