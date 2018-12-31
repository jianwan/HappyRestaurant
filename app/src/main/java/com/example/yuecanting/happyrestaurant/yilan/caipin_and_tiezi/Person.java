package com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi;

import cn.bmob.v3.BmobObject;

/**
 * Created by 17631 on 2018/12/28.
 * bean数据，person是指 该用户喜欢，收藏等的bean数据
 */

public class Person extends BmobObject {

    String userId;
    String xihuanId;
    String shoucangId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getXihuanId() {
        return xihuanId;
    }

    public void setXihuanId(String xihuanId) {
        this.xihuanId = xihuanId;
    }

    public String getShoucangId() {
        return shoucangId;
    }

    public void setShoucangId(String shoucangId) {
        this.shoucangId = shoucangId;
    }
}
