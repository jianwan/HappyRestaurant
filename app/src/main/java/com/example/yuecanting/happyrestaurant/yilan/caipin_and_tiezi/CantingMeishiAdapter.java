package com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.search.Meishi;

import java.util.List;

/**
 * Created by 17631 on 2018/12/26.
 * 餐厅美食recyclerview的adapter
 */

public class CantingMeishiAdapter extends BaseQuickAdapter<Meishi, BaseViewHolder> {

    public CantingMeishiAdapter(@LayoutRes int layoutResId, @Nullable List<Meishi> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Meishi item) {
        helper.setText(R.id.yilan_canting_recyclerviewitem_name, item.getName());
        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.yilan_canting_recyclerviewitem_pic));
        helper.setText(R.id.yilan_canting_recyclerviewitem_yishoufenxiang,item.getYishouNumber()+"份 已售"
                +"|"+"分享 "+ item.getFenxiangNumber()+"次");
    }

}
