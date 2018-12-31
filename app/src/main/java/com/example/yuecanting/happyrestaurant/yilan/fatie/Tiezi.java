package com.example.yuecanting.happyrestaurant.yilan.fatie;

import cn.bmob.v3.BmobObject;

/**
 * Created by 17631 on 2018/12/29.
 */

public class Tiezi extends BmobObject {

    private String userId;
    private String meishiId;
    private String cantingId;
    private String url;
    private String title;
    private String currentDate;
    private String content;
    private Float fuwu;
    private Float caipin;
    private Float dianmian;
    private Integer xihuan;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMeishiId() {
        return meishiId;
    }

    public void setMeishiId(String meishiId) {
        this.meishiId = meishiId;
    }

    public String getCantingId() {
        return cantingId;
    }

    public void setCantingId(String cantingId) {
        this.cantingId = cantingId;
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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getFuwu() {
        return fuwu;
    }

    public void setFuwu(Float fuwu) {
        this.fuwu = fuwu;
    }

    public Float getCaipin() {
        return caipin;
    }

    public void setCaipin(Float caipin) {
        this.caipin = caipin;
    }

    public Float getDianmian() {
        return dianmian;
    }

    public void setDianmian(Float dianmian) {
        this.dianmian = dianmian;
    }

    public Integer getXihuan() {
        return xihuan;
    }

    public void setXihuan(Integer xihuan) {
        this.xihuan = xihuan;
    }
}
