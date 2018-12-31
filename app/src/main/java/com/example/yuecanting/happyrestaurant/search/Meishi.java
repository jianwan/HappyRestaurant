package com.example.yuecanting.happyrestaurant.search;

import cn.bmob.v3.BmobObject;

/**
 * Created by 17631 on 2018/12/26.
 * 美食的bean类
 */

public class Meishi extends BmobObject {

    private String name;
    private String cantingId;
    private String content;
    private String url;
    private Integer fenxiangNumber;
    private Integer yishouNumber;
    private Integer xihuanNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCantingId() {
        return cantingId;
    }

    public void setCantingId(String cantingId) {
        this.cantingId = cantingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFenxiangNumber() {
        return fenxiangNumber;
    }

    public void setFenxiangNumber(Integer fenxiangNumber) {
        this.fenxiangNumber = fenxiangNumber;
    }

    public Integer getYishouNumber() {
        return yishouNumber;
    }

    public void setYishouNumber(Integer yishouNumber) {
        this.yishouNumber = yishouNumber;
    }

    public Integer getXihuanNumber() {
        return xihuanNumber;
    }

    public void setXihuanNumber(Integer xihuanNumber) {
        this.xihuanNumber = xihuanNumber;
    }
}
