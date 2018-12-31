package com.example.yuecanting.happyrestaurant.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by 17631 on 2018/12/25.
 * user的bean类
 */

public class User extends BmobUser {

    private String qianming;


    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }
}
