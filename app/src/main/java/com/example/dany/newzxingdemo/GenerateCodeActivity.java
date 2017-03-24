package com.example.dany.newzxingdemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class GenerateCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private LinearLayout mToolbarbackLl;
    private Button mGeneratecodeBtn;
    private String url;
    private ImageView mQrcodeIv;
    private CreateEnglishQRCodeAsyncTask mCreateEnglishQRCodeAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        url = getResources().getString(R.string.url_qrcode);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mToolbarbackLl = (LinearLayout) findViewById(R.id.toolbar_back);
        mToolbarbackLl.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        mTitleTv.setText("生成二维码");
        mGeneratecodeBtn = (Button) findViewById(R.id.btn_generatecode);
        mGeneratecodeBtn.setOnClickListener(this);
        mQrcodeIv = (ImageView) findViewById(R.id.iv_qcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_generatecode:
                mCreateEnglishQRCodeAsyncTask = new CreateEnglishQRCodeAsyncTask();
                mCreateEnglishQRCodeAsyncTask.execute(url);
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(mCreateEnglishQRCodeAsyncTask != null){
            mCreateEnglishQRCodeAsyncTask.cancel(true);
            mCreateEnglishQRCodeAsyncTask = null;
        }
        super.onDestroy();
    }

    class CreateEnglishQRCodeAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            return QRCodeEncoder.syncEncodeQRCode(params[0], BGAQRCodeUtil.dp2px(GenerateCodeActivity.this, 150), Color.parseColor("#000000"));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                Toast.makeText(GenerateCodeActivity.this, "生成二维码成功", Toast.LENGTH_SHORT).show();
                mQrcodeIv.setVisibility(View.VISIBLE);
                mQrcodeIv.setImageBitmap(bitmap);
            } else {
                Toast.makeText(GenerateCodeActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
