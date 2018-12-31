package com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.yilan.fatie.Tiezi;

import java.util.List;


/**
 * Created by 17631 on 2018/12/29.
 * 餐厅帖子recyclerview的adapter
 */

public class CantingTieziAdapter extends BaseQuickAdapter<Tiezi,BaseViewHolder> {


    public CantingTieziAdapter(@LayoutRes int layoutResId, @Nullable List<Tiezi> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Tiezi item) {
        helper.setText(R.id.yilan_canting_recyclerviewitem_name, item.getTitle());
        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.yilan_canting_recyclerviewitem_pic));
        helper.setText(R.id.yilan_canting_recyclerviewitem_yishoufenxiang,"发表时间:"+item.getCurrentDate());
    }

}
