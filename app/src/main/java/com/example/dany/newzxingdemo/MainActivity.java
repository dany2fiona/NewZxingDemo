package com.example.dany.newzxingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private Button mLongPressBtn;
    private Button mSelectAlbumBtn;
    private Button mQrcodeBtn;
    private Button mGeneratecodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);
        mTitleTv.setText("二维码功能组");
        mLongPressBtn = (Button) findViewById(R.id.btn_long_press);
        mSelectAlbumBtn = (Button) findViewById(R.id.btn_select_album);
        mQrcodeBtn = (Button) findViewById(R.id.btn_qrcode);
        mGeneratecodeBtn = (Button) findViewById(R.id.btn_generatecode);
        mLongPressBtn.setOnClickListener(this);
        mSelectAlbumBtn.setOnClickListener(this);
        mQrcodeBtn.setOnClickListener(this);
        mGeneratecodeBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_long_press:
                intent = new Intent(MainActivity.this,LongPressActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_select_album:
                intent = new Intent(MainActivity.this,SelectAlbumActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qrcode:
                intent = new Intent(MainActivity.this,QRcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_generatecode:
                intent = new Intent(MainActivity.this,GenerateCodeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
