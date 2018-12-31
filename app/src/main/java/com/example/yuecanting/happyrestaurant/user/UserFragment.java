package com.example.yuecanting.happyrestaurant.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuecanting.happyrestaurant.R;

import cn.bmob.v3.BmobUser;

/**
 * Created by 17631 on 2018/12/25.
 * UserFragment 主要是用于登录和退出，其他功能待完善
 */

public class UserFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout user_ralLay_userlogin,user_ralLay_dongtai,user_ralLay_lishi,
            user_ralLay_zhanneixin,user_ralLay_shezhi,user_ralLay_guanyuwomen,user_ralLay_dianzan,
            user_ralLay_zanzhu,user_ralLay_yinsi;

    private ImageView user_imageview;
    private TextView login_tv_username,login_tv_qianming;

    private UserFragment userFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user, null);


        initViews(view);

        return view;
    }


    private void initViews(View view) {

        login_tv_qianming = view.findViewById(R.id.tv_qianming);
        user_ralLay_userlogin = view.findViewById(R.id.user_relLay_userlogin);
        user_ralLay_dongtai = view.findViewById(R.id.user_ralLay_dongtai);
        user_ralLay_lishi = view.findViewById(R.id.user_ralLay_lishi);
        user_ralLay_zhanneixin = view.findViewById(R.id.user_ralLay_zhanneixin);
        user_ralLay_shezhi = view.findViewById(R.id.user_ralLay_shezhi);
        user_ralLay_guanyuwomen = view.findViewById(R.id.user_ralLay_guanyuwomen);
        user_ralLay_dianzan = view.findViewById(R.id.user_ralLay_dianzan);
        user_ralLay_zanzhu = view.findViewById(R.id.user_ralLay_zanzhu);
        user_ralLay_yinsi = view.findViewById(R.id.user_ralLay_yinsi);

        //判断用户是否登录 根据登录情况设置TextView
        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
        if(bmobUser != null){
            login_tv_username = view.findViewById(R.id.tv_username);
            login_tv_username.setText(bmobUser.getUsername());
            login_tv_qianming.setText("个人签名:"+((User) bmobUser).getQianming());
        }else{
            Toast.makeText(getActivity().getBaseContext(),"你还未登录,请及时登录",Toast.LENGTH_SHORT).show();
            user_ralLay_userlogin.isClickable();
            user_ralLay_userlogin.setOnClickListener(this);
        }


        user_ralLay_dongtai.setOnClickListener(this);
        user_ralLay_lishi.setOnClickListener(this);
        user_ralLay_zhanneixin.setOnClickListener(this);
        user_ralLay_shezhi.setOnClickListener(this);
        user_ralLay_guanyuwomen.setOnClickListener(this);
        user_ralLay_dianzan.setOnClickListener(this);
        user_ralLay_zanzhu.setOnClickListener(this);
        user_ralLay_yinsi.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_relLay_userlogin:
                Intent intentToSignup = new Intent(getActivity(),LoginActivity.class);
                startActivity(intentToSignup);
                break;
            case R.id.user_ralLay_dongtai:
            case R.id.user_ralLay_lishi:
            case R.id.user_ralLay_zhanneixin:
            case R.id.user_ralLay_shezhi:
            case R.id.user_ralLay_guanyuwomen:
            case R.id.user_ralLay_dianzan:
            case R.id.user_ralLay_zanzhu:
            case R.id.user_ralLay_yinsi:

                Toast.makeText(getActivity().getBaseContext(),"此功能待完善",Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
