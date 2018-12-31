package com.example.yuecanting.happyrestaurant.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuecanting.happyrestaurant.MainActivity;
import com.example.yuecanting.happyrestaurant.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 17631 on 2018/12/25.
 * 注册activity，手机号+验证码+密码 注册，注意下面代码未对验证码进行验证（小bug未解决，也是免费验证短信不够的原因）
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText user_signup_phonenumber,user_signup_code,user_signup_password,user_signup_passwordagain;
    private Button user_signup_getCode,user_signup_signup;
    private ImageView user_toolbar_back;
    private TextView user_goto_login;
    private boolean isCodeRight = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        initView();
    }

    private void initView() {

        user_signup_phonenumber = (EditText) findViewById(R.id.user_signup_phonenumber);
        user_signup_code = (EditText) findViewById(R.id.user_signup_code);
        user_signup_password = (EditText) findViewById(R.id.user_signup_password);
        user_signup_passwordagain = (EditText) findViewById(R.id.user_signup_passwordagain);

        user_signup_getCode = (Button) findViewById(R.id.user_signup_getCode);
        user_signup_signup = (Button) findViewById(R.id.user_signup_signup);
        user_toolbar_back = (ImageView) findViewById(R.id.user_toolbar_back);

        user_goto_login = (TextView) findViewById(R.id.usre_goto_login);

        user_signup_getCode.setOnClickListener(this);
        user_signup_signup.setOnClickListener(this);
        user_toolbar_back.setOnClickListener(this);
        user_goto_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        String phone = user_signup_phonenumber.getText().toString();
        String code = user_signup_code.getText().toString();
        String password = user_signup_password.getText().toString();
        String passwordagain = user_signup_passwordagain.getText().toString();


        switch (view.getId()){
            case R.id.user_signup_getCode:
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getBaseContext(),"手机号不能为空",Toast.LENGTH_LONG).show();
                }else {
                    getPhoneCode(phone);
                }
                break;
            case R.id.user_signup_signup:
                if (TextUtils.isEmpty(phone)&&TextUtils.isEmpty(password)){
                    Toast.makeText(getBaseContext(),"账号和密码不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getBaseContext(),"账号不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(getBaseContext(),"密码不能为空",Toast.LENGTH_LONG).show();
                }else {
                    if (password.equals(passwordagain)){
                        //注册
                        signUp();
//                        verifyCode(phone,code);
//                        if (isCodeRight == false){
//                            Toast.makeText(getBaseContext(),"验证码不正确",Toast.LENGTH_LONG).show();
//                        }else {
//                            signUp();
//                        }
                    }else {
                        Toast.makeText(getBaseContext(),"两次密码输入不一致",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.usre_goto_login:
                //跳转至登录页
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.user_toolbar_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }



    //获取短信验证码
    private void getPhoneCode(final String phone) {

        BmobSMS.requestSMSCode(phone, "注册验证码", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"发送验证码成功，短信ID：" + smsId + "\n",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //验证短信验证码
    private void verifyCode(final String phone, String code) {
        BmobSMS.verifySmsCode(phone,code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setMobilePhoneNumber(phone);
                    user.setMobilePhoneNumberVerified(true);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                isCodeRight = true;
                                Toast.makeText(getBaseContext(),"发送验证码成功" + "\n",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(),"绑定手机号码失败：" + e.getErrorCode(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(),"验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //注册
    private void signUp() {
        User bu = new User();
        bu.setUsername(user_signup_phonenumber.getText().toString());
        bu.setPassword(user_signup_password.getText().toString());
//        bu.setQianming("这是测试签名");
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User userbean, BmobException e) {
                if(e==null){
                    Toast.makeText(getBaseContext(),"注册成功" ,Toast.LENGTH_SHORT).show();

                    //跳转至主页
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),"注册失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
