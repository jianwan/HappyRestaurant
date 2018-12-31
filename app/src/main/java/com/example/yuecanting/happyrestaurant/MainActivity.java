package com.example.yuecanting.happyrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuecanting.happyrestaurant.search.SearchFragment;
import com.example.yuecanting.happyrestaurant.user.UserFragment;
import com.example.yuecanting.happyrestaurant.yilan.yilanfragment.YiLanFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;



/*
* Created by 17631 on 2018/12/25.
* 主页面，包含yiLanFragment、searchFragment、userFragment三个Fragment,接下来的分包也是依靠这个三个fragment分的（先找fragment）
* 依靠fragment + viewpager实现滑动
*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager mainViewpager;
    private YiLanFragment yiLanFragment;
    private SearchFragment searchFragment;
    private UserFragment userFragment;
    private MainViewpagerAdapter mainViewpagerAdapter;
    private ImageView ivOne,ivTwo,ivFour;
    private TextView tvOne,tvTwo,tvFour,tv_title,toolbar_loginout;
    private Toolbar toolbar;
    private TextView toolbarTextview;

    List<Fragment> fragmentList;

    //上次按下返回键的系统时间
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化bmob
        Bmob.initialize(this, "cf9a3a18587f5cef879917c784cbea1a");

        //初始化view和toolbar
        initView();
        initToolbar();

    }


    private void initView() {
        mainViewpager = (ViewPager) findViewById(R.id.activity_main_viewPager);
        fragmentList = new ArrayList<>();

        yiLanFragment = new YiLanFragment();
        searchFragment = new SearchFragment();
        userFragment = new UserFragment();

        fragmentList.add(yiLanFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(userFragment);


        mainViewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(),fragmentList);
        mainViewpager.setOffscreenPageLimit(3);  //设置预加载
        mainViewpager.setAdapter(mainViewpagerAdapter);
        toolbar_loginout=(TextView) findViewById(R.id.toolbar_loginout);
        toolbarTextview = (TextView) findViewById(R.id.toolbar_textview);
        ivOne = (ImageView) findViewById(R.id.linear1_iv);
        ivTwo = (ImageView) findViewById(R.id.linear2_iv);
        ivFour = (ImageView) findViewById(R.id.linear4_iv);
        tvOne = (TextView) findViewById(R.id.linear1_tv);
        tvTwo = (TextView) findViewById(R.id.linear2_tv);
        tvFour = (TextView) findViewById(R.id.linear4_tv);

        tv_title = (TextView) findViewById(R.id.toolbar_title);

        mainViewpager.setCurrentItem(0);  //设置当前选中的页面。
        //初始化txtView的颜色
        tvOne.setTextColor(this.getResources().getColor(R.color.main_toolbar));
        tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
        tvFour.setTextColor(this.getResources().getColor(R.color.textColor));

        toolbar_loginout.setOnClickListener(this);
        toolbarTextview.setOnClickListener(this);
        ivOne.setOnClickListener(this);
        ivTwo.setOnClickListener(this);
        ivFour.setOnClickListener(this);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);
        tvFour.setOnClickListener(this);

        mainViewpager.setOnPageChangeListener(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.background),100);


    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            tv_title.setText("一览");
        }
    }


    //点击事件，以后不再叙述
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear1_iv:
            case R.id.linear1_tv:
                mainViewpager.setCurrentItem(0);
                tv_title.setText("一览");
                toolbar_loginout.setVisibility(View.INVISIBLE);
                break;
            case R.id.linear2_iv:
            case R.id.linear2_tv:
                mainViewpager.setCurrentItem(1);
                tv_title.setText("搜索");
                toolbar_loginout.setVisibility(View.INVISIBLE);
                break;
            case R.id.linear4_iv:
            case R.id.linear4_tv:
                mainViewpager.setCurrentItem(2);
                tv_title.setText("个人中心");
                toolbar_loginout.setVisibility(View.VISIBLE);
                break;
            case R.id.toolbar_loginout:
                //退出登录
                BmobUser user = BmobUser.getCurrentUser();
                if (user != null){
                    BmobUser.logOut();
                    //跳转至主页
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getBaseContext(),"你还未登录",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }



    //viewpager 的三个方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                toolbar_loginout.setVisibility(View.INVISIBLE);
                toolbarTextview.setVisibility(View.GONE);
                tv_title.setText("首页");
                ivOne.setImageResource(R.mipmap.home_seclect);
                ivTwo.setImageResource(R.mipmap.search_unselect);
                ivFour.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
                tvFour.setTextColor(this.getResources().getColor(R.color.textColor));
                break;
            case 1:
                toolbar_loginout.setVisibility(View.INVISIBLE);
                toolbarTextview.setVisibility(View.GONE);
                tv_title.setText("搜索");
                ivOne.setImageResource(R.mipmap.home_unseclect);
                ivTwo.setImageResource(R.mipmap.search_select);
                ivFour.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.textColor));
                tvTwo.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvFour.setTextColor(this.getResources().getColor(R.color.textColor));
                break;

            case 2:
                toolbar_loginout.setVisibility(View.VISIBLE);
                tv_title.setText("个人中心");
                ivOne.setImageResource(R.mipmap.home_unseclect);
                ivTwo.setImageResource(R.mipmap.search_unselect);
                ivFour.setImageResource(R.mipmap.my_seclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.textColor));
                tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
                tvFour.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //按两次退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if(keyCode == KeyEvent.KEYCODE_BACK){
            currentBackTime = System.currentTimeMillis();
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else{ //如果两次按下的时间差小于2秒，则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
