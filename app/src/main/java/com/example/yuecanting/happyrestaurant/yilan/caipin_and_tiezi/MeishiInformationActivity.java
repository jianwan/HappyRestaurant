package com.example.yuecanting.happyrestaurant.yilan.caipin_and_tiezi;

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
import com.example.yuecanting.happyrestaurant.search.Meishi;
import com.example.yuecanting.happyrestaurant.user.LoginActivity;
import com.example.yuecanting.happyrestaurant.yilan.yilanfragment.Restaurant;
import com.example.yuecanting.happyrestaurant.yilan.fatie.TieziFabiaoAcitivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by 17631 on 2018/12/26.
 * 美食信息展现页面，用户可以喜欢该美食，还可以分享到微信/微信朋友圈
 * 该页喜欢的逻辑略复杂，需注意一下
 */

public class MeishiInformationActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView meishi_information_pic,meishi_information_cantingpic,meishi_information_xihuan,
            meishi_information_tiezi,meishi_information_fenxiang;


    private TextView meishi_information_content,meishi_information_xihuan_number,meishi_information_tiezitext,
            meishi_information_fenxiangtext,meishi_information_cantingname,meishi_information_cantingcontent;


    LinearLayout meishi_information_canting;

    Boolean isXihuanMeishi = false;  //是否对该美食进行过喜欢操作
    Boolean isLogin = false;        //是否正登录

    String meishipic;         //美食图片的url
    String cantingId;               //是该美食所属的餐厅的objectId
    String meishi_objectId;          //该美食的objectId
    Integer xihuanNumber = 0;      //该物品喜欢的数量



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yilan_canting_meishiinformation);
        initView();

        
        //接收美食的objectId
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        meishi_objectId = data.getString("meishi_objectId");

        //在Bmob上查询美食的一些信息，如图片，标题等
        quaryMeishiInformation(meishi_objectId);

        //根据用户是否登录 来 查询该美食是否被该用户喜欢
        BmobUser user = BmobUser.getCurrentUser();
        if (user != null){
            queryIsXihuan(meishi_objectId);
            isLogin = true;
        }else {
            isLogin = false;
        }


    }



    private void initView() {

        meishi_information_pic = (ImageView) findViewById(R.id.meishi_information_pic);
        meishi_information_cantingpic = (ImageView) findViewById(R.id.meishi_information_cantingpic);
        meishi_information_xihuan = (ImageView) findViewById(R.id.meishi_information_xihuan);
        meishi_information_tiezi = (ImageView) findViewById(R.id.meishi_information_tiezi);
        meishi_information_fenxiang = (ImageView) findViewById(R.id.meishi_information_fenxiang);

        meishi_information_content = (TextView) findViewById(R.id.meishi_information_content);
        meishi_information_xihuan_number = (TextView) findViewById(R.id.meishi_information_xihuan_number);
        meishi_information_tiezitext = (TextView) findViewById(R.id.meishi_information_tiezitext);
        meishi_information_fenxiangtext = (TextView) findViewById(R.id.meishi_information_fenxiangtext);
        meishi_information_cantingname = (TextView) findViewById(R.id.meishi_information_cantingname);
        meishi_information_cantingcontent = (TextView) findViewById(R.id.meishi_information_cantingcontent);

        meishi_information_canting = (LinearLayout) findViewById(R.id.meishi_information_canting);

        meishi_information_xihuan.setOnClickListener(this);
        meishi_information_xihuan_number.setOnClickListener(this);
        meishi_information_tiezi.setOnClickListener(this);
        meishi_information_tiezitext.setOnClickListener(this);
        meishi_information_fenxiang.setOnClickListener(this);
        meishi_information_fenxiangtext.setOnClickListener(this);
        meishi_information_canting.setOnClickListener(this);

    }

    private void quaryMeishiInformation(final String meishi_objectId) {
        BmobQuery<Meishi> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("objectId", meishi_objectId);
        categoryBmobQuery.findObjects(new FindListener<Meishi>() {
            @Override
            public void done(List<Meishi> object, BmobException e) {
                if (object.size() != 0) {
                    meishipic = object.get(0).getUrl();
                    xihuanNumber = object.get(0).getXihuanNumber();

                    Glide.with(getApplicationContext()).load(meishipic).into(meishi_information_pic);
                    meishi_information_content.setText(object.get(0).getContent());
                    meishi_information_xihuan_number.setText(xihuanNumber.toString());


                    cantingId = object.get(0).getCantingId();
                    BmobQuery<Restaurant> queryCanting = new BmobQuery<>();
                    queryCanting.addWhereEqualTo("objectId", cantingId);
                    queryCanting.findObjects(new FindListener<Restaurant>() {
                        @Override
                        public void done(List<Restaurant> object, BmobException e) {
                            if (e == null) {
                                meishi_information_cantingname.setText(object.get(0).getTitle());
                                meishi_information_cantingcontent.setText(object.get(0).getQianming());
                                Glide.with(getApplicationContext()).load(object.get(0).getUrl()).into(meishi_information_cantingpic);
                            } else {

                            }
                        }
                    });

                    Toast.makeText(getApplicationContext(),"查询成功：共有" + object.size()+"条数据",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //看该用户是否喜欢过该美食
    private void queryIsXihuan(String meishi_objectId) {
        //查询条件1
        BmobQuery<Person> queryPerson1 = new BmobQuery<>();
        queryPerson1.addWhereEqualTo("userId",BmobUser.getCurrentUser().getObjectId());
        //查询条件2
        BmobQuery<Person> queryPerson2 = new BmobQuery<>();
        queryPerson2.addWhereEqualTo("xihuanId",meishi_objectId);
        //复合查询
        List<BmobQuery<Person>> andQuerys = new ArrayList<BmobQuery<Person>>();
        andQuerys.add(queryPerson1);
        andQuerys.add(queryPerson2);
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.and(andQuerys);

        Log.d("TAGAAA",BmobUser.getCurrentUser().getObjectId()+meishi_objectId);
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if (list.size() != 0){
                    isXihuanMeishi = true;
                    meishi_information_xihuan.setImageResource(R.drawable.xihuaned);
                    Toast.makeText(getApplicationContext(),"你曾经喜欢过该美食",Toast.LENGTH_SHORT).show();
                }else {
                    isXihuanMeishi = false;
                    meishi_information_xihuan.setImageResource(R.drawable.xihuan);
                }
            }
        });
    }


    //不喜欢-1
    private void buxihuan() {
        Meishi meishi = new Meishi();
        final Integer number = xihuanNumber - 1 ;
        meishi.setXihuanNumber(number);
        meishi.update(meishi_objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{

                }
            }
        });


        //查询条件1
        BmobQuery<Person> queryPerson1 = new BmobQuery<>();
        queryPerson1.addWhereEqualTo("userId",BmobUser.getCurrentUser().getObjectId());
        //查询条件2
        BmobQuery<Person> queryPerson2 = new BmobQuery<>();
        queryPerson2.addWhereEqualTo("xihuanId",meishi_objectId);
        //复合查询
        List<BmobQuery<Person>> andQuerys = new ArrayList<BmobQuery<Person>>();
        andQuerys.add(queryPerson1);
        andQuerys.add(queryPerson2);
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.and(andQuerys);

        query.findObjects(new FindListener<Person>() {
           @Override
           public void done(List<Person> list, BmobException e) {
               Person person = new Person();
               person.setObjectId(list.get(0).getObjectId());
               person.delete(new UpdateListener() {
                   @Override
                   public void done(BmobException e) {
                       if(e==null){
                           meishi_information_xihuan.setImageResource(R.drawable.xihuan);
                           meishi_information_xihuan_number.setText(number.toString());
                           Toast.makeText(getApplicationContext(),"取消喜欢成功",Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(getApplicationContext(),"取消喜欢失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });


    }

    //喜欢+1
    private void xihuan() {
        Meishi meishi = new Meishi();
        final Integer number = xihuanNumber +1 ;
        meishi.setXihuanNumber(number);
        meishi.update(meishi_objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{

                }
            }
        });

        Person person = new Person();
        person.setXihuanId(meishi_objectId);
        person.setUserId(BmobUser.getCurrentUser().getObjectId());
        person.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    meishi_information_xihuan.setImageResource(R.drawable.xihuaned);
                    meishi_information_xihuan_number.setText(number.toString());
                    Toast.makeText(getApplicationContext(),"喜欢成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"喜欢失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    //点击事件，注意喜欢的逻辑，先查询用户是否喜欢过该美食，如果喜欢过则标记为喜欢，没有则不标记，
    // 点击喜欢则标记为喜欢，帖子的喜欢数量+1，反之，减一
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.meishi_information_xihuan:

                if (isLogin == true){
                    if (isXihuanMeishi == true){
                        buxihuan();
                    }else {
                        xihuan();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"你还未登录,已跳转至登录页面",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,LoginActivity.class));
                }

                break;
            case R.id.meishi_information_tiezi:
            case R.id.meishi_information_tiezitext:

                if (isLogin == true){

                    //传递meishiId和cantingId
                    Intent intent = new Intent(getBaseContext(), TieziFabiaoAcitivity.class);
                    Bundle data = new Bundle();
                    data.putString("meishi_objectId",meishi_objectId);
                    data.putString("cantingId",cantingId);
                    intent.putExtra("data",data);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"你还未登录,已跳转至登录页面",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;
            case R.id.meishi_information_fenxiang:
            case R.id.meishi_information_fenxiangtext:

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
            case R.id.meishi_information_canting:

                break;

        }
    }


}
