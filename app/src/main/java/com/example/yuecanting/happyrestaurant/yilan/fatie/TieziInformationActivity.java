package com.example.yuecanting.happyrestaurant.yilan.fatie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yuecanting.happyrestaurant.R;
import com.example.yuecanting.happyrestaurant.user.User;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by 17631 on 2018/12/29.
 */

public class TieziInformationActivity extends AppCompatActivity implements View.OnClickListener {



    private ImageView tiezi_information_pic,tiezi_information_userpic,tiezi_information_xihuan,
            tiezi_information_shoucang,tiezi_information_fenxiang;
    private TextView tiezi_information_title,tiezi_information_xihuan_number,tiezi_information_shoucangtext,
            tiezi_information_fenxiangtext,tiezi_information_username,tiezi_information_qianming,
            tiezi_information_currenttime,tiezi_information_content,tiezi_information_pingfen;


    private String meishipic;                //美食图片的url
    private String meishi_objectId;          //该美食的objectId
    private Integer xihuanNumber = 0;         //该物品喜欢的数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meishi_tiezi_information);

        initView();

        //接收帖子的objectId
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        String tiezi_objectId = data.getString("tiezi_objectId");

        quaryTieziInformation(tiezi_objectId);


        
    }

    private void initView() {
        tiezi_information_pic = (ImageView) findViewById(R.id.tiezi_information_pic);
        tiezi_information_userpic = (ImageView) findViewById(R.id.tiezi_information_userpic);
        tiezi_information_xihuan = (ImageView) findViewById(R.id.tiezi_information_xihuan);
        tiezi_information_shoucang = (ImageView) findViewById(R.id.tiezi_information_shoucang);
        tiezi_information_fenxiang = (ImageView) findViewById(R.id.tiezi_information_fenxiang);

        tiezi_information_title = (TextView) findViewById(R.id.tiezi_information_title);
        tiezi_information_xihuan_number = (TextView) findViewById(R.id.tiezi_information_xihuan_number);
        tiezi_information_shoucangtext = (TextView) findViewById(R.id.tiezi_information_shoucangtext);
        tiezi_information_fenxiangtext = (TextView) findViewById(R.id.tiezi_information_fenxiangtext);
        tiezi_information_username = (TextView) findViewById(R.id.tiezi_information_username);
        tiezi_information_qianming = (TextView) findViewById(R.id.tiezi_information_qianming);
        tiezi_information_currenttime = (TextView) findViewById(R.id.tiezi_information_currenttime);
        tiezi_information_content = (TextView) findViewById(R.id.tiezi_information_content);
        tiezi_information_pingfen = (TextView) findViewById(R.id.tiezi_information_pingfen);

        tiezi_information_xihuan.setOnClickListener(this);
        tiezi_information_shoucang.setOnClickListener(this);
        tiezi_information_shoucangtext.setOnClickListener(this);
        tiezi_information_fenxiang.setOnClickListener(this);
        tiezi_information_fenxiangtext.setOnClickListener(this);

    }

    //查询该objectId的帖子的信息
    private void quaryTieziInformation(final String tiezi_objectId) {
        BmobQuery<Tiezi> queryTiezi = new BmobQuery<>();
        queryTiezi.addWhereEqualTo("objectId",tiezi_objectId);
        queryTiezi.findObjects(new FindListener<Tiezi>() {
            @Override
            public void done(List<Tiezi> list, BmobException e) {
                if (e == null){

                    xihuanNumber = list.get(0).getXihuan();
                    meishipic = list.get(0).getUrl();

                    Glide.with(getApplicationContext()).load(list.get(0).getUrl()).into(tiezi_information_pic);
                    tiezi_information_pingfen.setText("   店面评分:"+ list.get(0).getDianmian()+"   菜品评分"+list.get(0).getCaipin()+"  服务评分:"+list.get(0).getFuwu());
                    tiezi_information_title.setText(list.get(0).getTitle());
                    tiezi_information_xihuan_number.setText(xihuanNumber.toString());
                    tiezi_information_currenttime.setText("  "+list.get(0).getCurrentDate());
                    tiezi_information_content.setText("  "+list.get(0).getContent());

                    String userId = list.get(0).getUserId();
                    BmobQuery<User> query = new BmobQuery<>();
                    query.getObject(userId, new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            tiezi_information_username.setText(user.getUsername());
                            tiezi_information_qianming.setText(user.getQianming());
                        }
                    });
                }else {

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tiezi_information_xihuan:
                Toast.makeText(getBaseContext(),"此功能待实现",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tiezi_information_shoucang:
            case R.id.tiezi_information_shoucangtext:

                Toast.makeText(getBaseContext(),"此功能待实现",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tiezi_information_fenxiang:
            case R.id.tiezi_information_fenxiangtext:


                //分享美食图片到微信好友和微信朋友圈
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View   dialog = inflater.inflate(R.layout.share_dialog,(ViewGroup) findViewById(R.id.share_dialog));
                builder.setTitle("分享美食图片至");
                builder.setView(dialog);
                builder.show();
                LinearLayout wechat = dialog.findViewById(R.id.share_wechat);
                LinearLayout wechatmoments = dialog.findViewById(R.id.share_wechatmoments);
                wechat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToWechat();
                    }

                    private void shareToWechat() {
                        Platform.ShareParams params = new Platform.ShareParams();
                        params.setShareType(Platform.SHARE_IMAGE);
                        params.setImageUrl(meishipic);
                        params.setText("text");
                        params.setTitle("title");
                        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                        //该回调实际上不会，因为是绕过了微信审核
                        wechat.setPlatformActionListener(new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                Log.d("TAG","cpmplete");
                                Toast.makeText(getApplicationContext(),"分享完成",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                Log.d("TAG","error"+throwable);
                                Toast.makeText(getApplicationContext(),"分享失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getApplicationContext(),"取消分享",Toast.LENGTH_SHORT).show();
                                Log.d("TAG","cancel");
                            }
                        });
                        wechat.share(params);
                    }
                });
                wechatmoments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToWechaMoment();
                    }

                    private void shareToWechaMoment() {
                        Platform.ShareParams params = new Platform.ShareParams();
                        params.setShareType(Platform.SHARE_IMAGE);
                        params.setImageUrl(meishipic);
                        params.setText("text");
                        params.setTitle("title");
                        Platform wechat = ShareSDK.getPlatform(WechatMoments.NAME);
                        wechat.setPlatformActionListener(new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                Log.d("TAG","cpmplete");
                                Toast.makeText(getApplicationContext(),"分享完成",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                Log.d("TAG","error"+throwable);
                                Toast.makeText(getApplicationContext(),"分享失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getApplicationContext(),"取消分享",Toast.LENGTH_SHORT).show();
                                Log.d("TAG","cancel");
                            }
                        });
                        wechat.share(params);
                    }
                });

                break;
        }

    }
}
