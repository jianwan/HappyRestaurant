package com.example.yuecanting.happyrestaurant.yilan.yilanfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi.CantingActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 17631 on 2018/12/25.
 * YiLanFragment, 这个yilan下面分了三个包，分别是yilanfragment（即一览首页），caipin and tiezi（每个餐厅点进去展现的菜品和帖子），fatie（发帖，内容比较多就单独分包了）
 * 实现技术：recyclerview + adapter
 * 其他如下注释
 */

public class YiLanFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Restaurant> data = new ArrayList<>();
    private ArrayList<Restaurant> newData = new ArrayList<>();

    private int first = 0;
    private int last = 4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yilian, null);


        //查询餐厅 并 添加点击跳转事件
        quaryData(first,last);

        //初始化recyclerview相关
        initRecyclerview(view);

        return view;
    }


    private void initRecyclerview(View view) {

        recyclerView = view.findViewById(R.id.yilan_recyclerview);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);

        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());

    }



    private void quaryData(final int first1, final int last1) {
        BmobQuery<Restaurant> query1 = new BmobQuery<>();
        query1.addWhereGreaterThanOrEqualTo("Id", first1);
        BmobQuery<Restaurant> query2 = new BmobQuery<>();
        query2.addWhereLessThanOrEqualTo("Id", last1);
        List<BmobQuery<Restaurant>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<Restaurant> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.and(queries);
        categoryBmobQuery.findObjects(new FindListener<Restaurant>() {
            @Override
            public void done(final List<Restaurant> object, BmobException e) {
                if (e == null) {
                    data.addAll(object);
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(),data,true);
                    //设置Adapter
                    recyclerView.setAdapter(recyclerViewAdapter);
                    first = first +5; last = last + 5;
                    Toast.makeText(getContext(),"查询成功：" + object.size()+"条数据",Toast.LENGTH_SHORT).show();
                    recyclerViewAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), CantingActivity.class);
                            Bundle bundleData = new Bundle();
                            //传递餐厅的objectId
                            bundleData.putString("objectId",data.get(position).getObjectId());
                            bundleData.putString("name",data.get(position).getTitle());
                            intent.putExtra("data",bundleData);
                            startActivity(intent);
                        }
                    });


                    if (last < 15){
                        recyclerViewAdapter.setOnItemClickLitener2(new RecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //点击加载更多,每次加载四个数据
                                loadMoreData(first,last);
                            }
                        });
                    }else {
                        Toast.makeText(getContext(),"加载完毕",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(),"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void loadMoreData(int first1, final int last1) {
        BmobQuery<Restaurant> query1 = new BmobQuery<>();
        query1.addWhereGreaterThanOrEqualTo("Id", first1);
        BmobQuery<Restaurant> query2 = new BmobQuery<>();
        query2.addWhereLessThanOrEqualTo("Id", last1);
        List<BmobQuery<Restaurant>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<Restaurant> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.and(queries);
        categoryBmobQuery.findObjects(new FindListener<Restaurant>() {
            @Override
            public void done(final List<Restaurant> object, BmobException e) {
                if (e == null) {
                    newData.clear();
                    newData.addAll(object);
                    first = first +5; last = last + 5;
                    recyclerViewAdapter.updateList(newData,true);
                    if (last > 15){
                        Toast.makeText(getContext(),"数据加载完毕",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
