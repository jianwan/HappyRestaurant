package com.example.yuecanting.happyrestaurant.yilan.yilanfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuecanting.happyrestaurant.R;

import java.util.List;


/**
 * Created by 17631 on 2018/12/26.
 * recyclerview的adapter
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemClickListener mOnItemClickListener2 = null;
    private List<Restaurant> datas;
    private Context context;

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示



    public RecyclerViewAdapter(Context context, List<Restaurant> datas,boolean hasMore){
        this.context = context;
        this.datas = datas;
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.fragment_yilan_recyclerviewitem, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.fragment_yilan_recyclerviewitem_footview, null));
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 如果是正常的item，直接设置item的值
        if (holder instanceof NormalHolder) {
            ((NormalHolder)holder).name.setText(datas.get(position).getTitle());
            ((NormalHolder)holder).content.setText(datas.get(position).getQianming());
            Glide.with(context).load(datas.get(position).getUrl()).into(((NormalHolder)holder).pic);
            //通过为条目设置点击事件触发回调
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                });
            }
        } else {

            if (datas.size() < 14){
                ((FootHolder) holder).tips.setText("点击加载更多");
            }else if (datas.size() == 14){
                ((FootHolder) holder).tips.setText("数据加载完毕！");
            }
            ((FootHolder)holder).tips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener2.onItemClick(view, position);
                }
            });
        }

    }



    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<Restaurant> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }


    public static class NormalHolder extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView name,content;

        public NormalHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.yilan_item_name);
            content = itemView.findViewById(R.id.yilan_item_content);
            pic = itemView.findViewById(R.id.yilan_item_pic);
        }
    }


    // // 底部footView的ViewHolder，用以缓存findView操作
    public static class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.yilan_recyclerview_footview);
        }
    }



    //设置回调接口
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public void setOnItemClickLitener2(OnItemClickListener mOnItemClickListener2){
        this.mOnItemClickListener2 = mOnItemClickListener2;
    }


    @Override
    public int getItemCount() {
        return datas.size()+1;
    }


    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }




}
