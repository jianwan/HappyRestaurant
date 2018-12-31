package com.example.yuecanting.happyrestaurant.search;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yuecanting.happyrestaurant.R;

import java.util.List;

/**
 * Created by 17631 on 2018/12/26.
 * SearchFragment里recyclerview的adapter
 */

public class SearchAdapter extends BaseQuickAdapter<Meishi, BaseViewHolder> {

    public SearchAdapter(@LayoutRes int layoutResId, @Nullable List<Meishi> data) {
        super(layoutResId, data);
    }

    public SearchAdapter(@Nullable List<Meishi> data) {
        super(data);
    }

    public SearchAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Meishi item) {

        helper.setText(R.id.search_item_content, item.getName());
        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.search_item_pic));

    }
}
