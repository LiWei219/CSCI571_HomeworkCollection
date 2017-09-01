package com.example.myhw9;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/4/23.
 */

public class PostListAdapter extends BaseAdapter{
    private Context mContext;
    private LinkedList<postitem> mObj;

    public PostListAdapter(){};

    public PostListAdapter(LinkedList<postitem> mObj, Context mContext){
        this.mObj = mObj;
        this.mContext = mContext;
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

    private class ViewHolder{
        ImageView itemimg;
        TextView itemtime;
        TextView itemname;
        TextView itemcontent;
    }
    public PostListAdapter.ViewHolder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.postlistitem,parent,false);
            holder = new PostListAdapter.ViewHolder();
            holder.itemimg = (ImageView) convertView.findViewById(R.id.itemimg);
            holder.itemcontent = (TextView) convertView.findViewById(R.id.itemcontent);
            holder.itemname = (TextView)convertView.findViewById(R.id.itemname);
            holder.itemtime = (TextView)convertView.findViewById(R.id.itemtime);
            convertView.setTag(holder);
        }else{
            holder = (PostListAdapter.ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(mObj.get(position).getPicurl()).fit().into(holder.itemimg);

        holder.itemname.setText(mObj.get(position).getName());

        holder.itemtime.setText(mObj.get(position).getTime());
        if(!mObj.get(position).getMessage().equals("")){
            holder.itemcontent.setText(mObj.get(position).getMessage());
        }else if(!mObj.get(position).getStory().equals("")){
            holder.itemcontent.setText(mObj.get(position).getStory());
        }else
            holder.itemcontent.setText("No message available");

        return convertView;
    }
}
