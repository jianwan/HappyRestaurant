package com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.search.Meishi;
import com.example.yuecanting.happyrestaurant.yilan.fatie.Tiezi;
import com.example.yuecanting.happyrestaurant.yilan.fatie.TieziInformationActivity;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by 17631 on 2018/12/26.
 * yilanFragment点进来就是这个页面，查找并展现餐厅的菜品和发的帖子
 */

public class CantingActivity extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerView2;
    private CantingMeishiAdapter cantingMeishiAdapter;
    private CantingTieziAdapter cantingTieziAdapter;
    ArrayList<Meishi> dataMeishi = new ArrayList<>();
    ArrayList<Tiezi> dataTiezi = new ArrayList<>();
    private TextView yilan_canting_toolbartitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.yilan_canting);


        //接受跳转过来的餐厅的id，依次来查找该餐厅的菜品和帖子的信息
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        String objectId = data.getString("objectId");
        String name = data.getString("name");
        quaryMeishi(objectId);
        queryTiezi(objectId);

        //初始化Mob的shareSDK（作用是分享到微信）,下面两个也是初始化
        initMob();

        yilan_canting_toolbartitle = (TextView) findViewById(R.id.yilan_canting_toolbartitle);
        yilan_canting_toolbartitle.setText(name);

        initRecyclerview();


    }

    private void initRecyclerview() {

        //该餐厅美食的recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.yilan_canting_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);


        //该餐厅发表的帖子的recyclerview
        recyclerView2 = (RecyclerView) findViewById(R.id.yilan_canting_recyclerview_tiezi);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        //设置布局管理器
        recyclerView2.setLayoutManager(layoutManager2);
        //设置为水平布局
        layoutManager2.setOrientation(OrientationHelper.HORIZONTAL);
    }

    private void initMob() {
        MobSDK.init(getApplicationContext(),"297d0c9cf063c","4e3b34e24cf1c9f5b3f0a741311e4ebe");
        //配置微信配置
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Id","1");
        hashMap.put("SortId","1");
        hashMap.put("AppKey","297d0c9cf063c");
        hashMap.put("AppSecret","4e3b34e24cf1c9f5b3f0a741311e4ebe");
        hashMap.put("RedirectUrl","http://www.sharesdk.cn");
        hashMap.put("ShareByAppClient","true");
        hashMap.put("Enable","true");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME,hashMap);
    }


    //查找该餐厅发表的帖子
    private void queryTiezi(String objectId) {
        BmobQuery<Tiezi> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("cantingId", objectId);
        categoryBmobQuery.order("createdAt");
        categoryBmobQuery.findObjects(new FindListener<Tiezi>() {
            @Override
            public void done(final List<Tiezi> list, BmobException e) {
                if (e == null){
                    dataTiezi.addAll(list);
                    cantingTieziAdapter = new CantingTieziAdapter(R.layout.yilan_canting_recyclerviewitem,dataTiezi);
                    recyclerView2.setAdapter(cantingTieziAdapter);
                    Toast.makeText(getBaseContext(),"查询成功：共有" + list.size()+"条帖子",Toast.LENGTH_SHORT).show();

                    cantingTieziAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getBaseContext(), TieziInformationActivity.class);
                            Bundle data = new Bundle();
                            //传递帖子的objectId
                            data.putString("tiezi_objectId",list.get(position).getObjectId());
                            intent.putExtra("data",data);
                            startActivity(intent);
                        }
                    });

                }else {
                    Toast.makeText(getBaseContext(),"查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //查找该餐厅的美食
    private void quaryMeishi(final String objectId) {
        BmobQuery<Meishi> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("cantingId", objectId);
        categoryBmobQuery.findObjects(new FindListener<Meishi>() {
            @Override
            public void done(final List<Meishi> object, BmobException e) {
                if (e == null) {
                    dataMeishi.addAll(object);
                    cantingMeishiAdapter = new CantingMeishiAdapter(R.layout.yilan_canting_recyclerviewitem,dataMeishi);
                    //设置Adapter
                    recyclerView.setAdapter(cantingMeishiAdapter);
//                    Toast.makeText(getBaseContext(),"查询成功：共有" + object.size()+"条数据",Toast.LENGTH_SHORT).show();


                    cantingMeishiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            Intent intent = new Intent(getBaseContext(), MeishiInformationActivity.class);
                            Bundle data = new Bundle();
                            //传递美食的objectId
                            data.putString("meishi_objectId",object.get(position).getObjectId());
                            intent.putExtra("data",data);
                            startActivity(intent);
//                            Toast.makeText(getBaseContext(),"查询成功：" + object.get(position).getObjectId(),Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getBaseContext(),"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
