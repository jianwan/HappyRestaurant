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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by 17631 on 2018/12/25.
 * 登录activity，无复杂逻辑
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText user_login_phonenumber,user_login_password;

    private Button user_login_login,user_login_weixin;

    private ImageView back;

    private TextView usre_goto_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initView();
    }

    private void initView() {

        user_login_phonenumber = (EditText) findViewById(R.id.user_login_phonenumber);
        user_login_password = (EditText) findViewById(R.id.user_login_password);
        user_login_login = (Button) findViewById(R.id.user_login_login);
        user_login_weixin = (Button) findViewById(R.id.user_login_weixin);
        back = (ImageView) findViewById(R.id.login_toolbar_back);
        usre_goto_signup = (TextView) findViewById(R.id.usre_goto_signup);

        user_login_login.setOnClickListener(this);
        user_login_weixin.setOnClickListener(this);
        back.setOnClickListener(this);
        usre_goto_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_toolbar_back:
                onBackPressed();
                break;
            case R.id.user_login_login:
                String phone = user_login_phonenumber.getText().toString();
                String password = user_login_password.getText().toString();

                if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(password)){
                    login(phone,password);
                }else {
                    Toast.makeText(getBaseContext(),"账号密码不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.user_login_weixin:
                Toast.makeText(getBaseContext(),"此功能待完善",Toast.LENGTH_SHORT).show();
                break;
            case R.id.usre_goto_signup:
                Intent intentToSignup = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intentToSignup);
                break;
        }

    }

    private void login(String phone, String password) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount(phone, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //跳转至主页
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(),"登录成功"+user.getUsername(),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(),"登录失败,账号或密码错误" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
