package com.example.dany.newzxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dany.newzxingdemo.task.DecodeQRCodeAsyncTask;

public class LongPressActivity extends AppCompatActivity implements View.OnLongClickListener,DecodeQRCodeAsyncTask.Listener{
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private LinearLayout mToolbarbackLl;
    private ImageView mQcodeIv;
    private DecodeQRCodeAsyncTask mDecodeQRCodeAsyncTask;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_press);
        initView();
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.qcode);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mToolbarbackLl = (LinearLayout) findViewById(R.id.toolbar_back);
        mToolbarbackLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolbar);
        mTitleTv.setText("长按识别二维码");
        mQcodeIv = (ImageView) findViewById(R.id.iv_qcode);
        mQcodeIv.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.iv_qcode:
                mDecodeQRCodeAsyncTask = new DecodeQRCodeAsyncTask(LongPressActivity.this,this);
                mDecodeQRCodeAsyncTask.execute(bitmap);
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDecodeQRCodeAsyncTask != null) {
            mDecodeQRCodeAsyncTask.cancel(true);
            mDecodeQRCodeAsyncTask = null;
        }
    }

    @Override
    public void onTaskSuccessed(String content) {
        //调用所有浏览器，选择一种打开
        final Uri uri = Uri.parse(content);
        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
//                //调用原生浏览器--一般不采用此方式
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(result);
//                intent.setData(content_url);
//                intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
//                startActivity(intent);
    }

}
