package com.example.yuecanting.happyrestaurant.yilan.yilanfragment;

import cn.bmob.v3.BmobObject;

/**
 * Created by 17631 on 2018/12/26.
 * 餐厅的bean数据
 */

public class Restaurant extends BmobObject{

    private String Id;
    private String Qianming;
    private String url;
    private String title;
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getQianming() {
        return Qianming;
    }

    public void setQianming(String qianming) {
        Qianming = qianming;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
