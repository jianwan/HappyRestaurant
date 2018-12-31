package com.example.yuecanting.happyrestaurant.search;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi.MeishiInformationActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by 17631 on 2018/12/25.
 * SearchFragment，第三方框架SearchView（搜索框） + recyclerview （展现搜索出来的内容）
 */

public class SearchFragment extends Fragment {

    private SearchView searchView;

    private RecyclerView recyclerView;
    private SearchAdapter recyclerViewSearchAdapter;
    ArrayList<Meishi> data = new ArrayList<>();
    Boolean isFirstQuery = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);



        // 绑定view
        searchView = view.findViewById(R.id.search_view);
        initRecyclerview(view);



        // 点击键盘搜索按钮进行搜索，需注意的是 精确搜索（按名字搜索），不是模糊搜索，
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                queryData(string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

            }
        });


        return view;
    }


    private void initRecyclerview(View view) {
        //设置布局管理器
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.search_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);

        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }


    private void queryData(String string) {

            BmobQuery<Meishi> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("name",string);
            categoryBmobQuery.findObjects(new FindListener<Meishi>() {
                @Override
                public void done(List<Meishi> object, BmobException e) {
                    if (e == null) {
                        Toast.makeText(getContext(),"查询成功：" + object.size(),Toast.LENGTH_SHORT).show();
                        if (isFirstQuery==false){
                            data.clear();
                        }
                        data.addAll(object);
                        isFirstQuery = false;

                        recyclerViewSearchAdapter = new SearchAdapter(R.layout.fragment_search_item,data);
                        //设置Adapter
                        recyclerView.setAdapter(recyclerViewSearchAdapter);


                        recyclerViewSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(getContext(), MeishiInformationActivity.class);
                                Bundle data = new Bundle();
                                //传递美食的objectId
                                data.putString("meishi_objectId",recyclerViewSearchAdapter.getItem(position).getObjectId());
                                intent.putExtra("data",data);
                                startActivity(intent);
                            }
                        });

                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText(getContext(),"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }


}
