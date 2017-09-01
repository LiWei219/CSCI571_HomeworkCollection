package com.example.myhw9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Administrator on 2017/4/23.
 */

public class detail extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public static Context dcontext;
    public static String id;
    public String[] str;
    public SPUtils sp;
    public Map<String,?> spmap;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Intent intent = getIntent();
        String infostr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        str = infostr.split("#");
        id = str[0];
        spmap = sp.getAll(MainActivity.mainContext);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast toast=Toast.makeText(getApplicationContext(),"You shared this post!",Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onCancel() {
                Toast toast=Toast.makeText(getApplicationContext(),"Sharing cancelled.",Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast toast=Toast.makeText(getApplicationContext(),"Sharing error.",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        viewPager = (ViewPager) findViewById(R.id.detview);
        tabLayout = (TabLayout) findViewById(R.id.dettab);

        dcontext = detail.this;

        initViewPager();
    }

    public void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("Albums");
        titles.add("Posts");
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new Fragmentalbum());
        fragments.add(new Fragmentpost());

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(mFragmentAdapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

        TextView tabzero = (TextView) LayoutInflater.from(this).inflate(R.layout.tab, null);
        tabzero.setText("                   Albums");
        tabzero.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.albums, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabzero);

        TextView tabone = (TextView) LayoutInflater.from(this).inflate(R.layout.tab, null);
        tabone.setText("                      Posts");
        tabone.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.posts, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.detail_menu, menu);
        if(spmap.containsKey(str[0])){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addfav) {
            Log.e(TAG,"Selected add to Favorites");

            String infostr = str[1]+"#"+str[2]+"#"+str[3];
            if(!spmap.containsKey(str[0])){
                sp.put(MainActivity.mainContext,str[0],infostr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Added to Favorites!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }else if(id==R.id.share){
            Log.e(TAG,"Selected Share details");
            String imageurl = str[3];
            Toast.makeText(getApplicationContext(),"Sharing "+str[1]+"!",Toast.LENGTH_LONG).show();

            /*    SharePhoto.Builder build = new SharePhoto.Builder();
                build.setImageUrl(Uri.parse(imageurl));
                build.setCaption("FB SEARCH FROM USC CSCI571");
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(build.build())
                        .build();

                shareDialog.show(content);  */

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(str[1])
                            .setContentDescription(
                                    "FB SEARCH FROM USC CSCI571")
                            .setImageUrl(Uri.parse(str[3]))
                         //   .setContentUrl(Uri.parse("http://www.facebook.com/"+str[0]))
                            .build();
                    Log.e(TAG,"linkContent build.");
                //    shareDialog.show(linkContent, ShareDialog.Mode.NATIVE);
                //    shareDialog.show(linkContent);
                    shareDialog.show(linkContent, ShareDialog.Mode.FEED);
                //    shareDialog.show(linkContent, ShareDialog.Mode.WEB);
                    Log.e(TAG,"show dialog");
                }else{
                    Toast.makeText(this, "NOT CALLED", Toast.LENGTH_SHORT).show();
                }

        }else if(id==R.id.removefav){
            if(spmap.containsKey(str[0])){
                sp.remove(MainActivity.mainContext,str[0]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Removed from Favorites!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"onactivityresult called");
    }
}
