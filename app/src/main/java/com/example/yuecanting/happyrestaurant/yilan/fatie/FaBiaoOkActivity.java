package com.example.yuecanting.happyrestaurant.yilan.fatie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yuecanting.happyrestaurant.MainActivity;
import com.example.yuecanting.happyrestaurant.R;

/**
 * Created by 17631 on 2018/12/29.
 */

public class FaBiaoOkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meishi_tiezi_fabiaook);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
