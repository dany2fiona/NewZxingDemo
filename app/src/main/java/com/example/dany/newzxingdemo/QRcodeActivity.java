package com.example.dany.newzxingdemo;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class QRcodeActivity extends AppCompatActivity implements QRCodeView.Delegate,View.OnClickListener{
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private LinearLayout mToolbarbackLl;
    private QRCodeView mQrCodeView;
    private Button mOpenFlashBtn;
    private Button mCloseFlashBtn;
    private Button mBarCodeBtn;
    private Button mQrCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mToolbarbackLl = (LinearLayout) findViewById(R.id.toolbar_back);
        mToolbarbackLl.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        mTitleTv.setText("扫描二维码");
        mQrCodeView = (QRCodeView) findViewById(R.id.zxingview);
        mQrCodeView.setDelegate(this);
        mOpenFlashBtn = (Button) findViewById(R.id.btn_open_flash);
        mCloseFlashBtn = (Button) findViewById(R.id.btn_close_flash);
        mBarCodeBtn = (Button) findViewById(R.id.btn_barcode);
        mQrCodeBtn = (Button) findViewById(R.id.btn_qrcode);
        mOpenFlashBtn.setOnClickListener(this);
        mCloseFlashBtn.setOnClickListener(this);
        mBarCodeBtn.setOnClickListener(this);
        mQrCodeBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQrCodeView.startCamera();
        mQrCodeView.showScanRect();
        mQrCodeView.startSpot();
    }

    @Override
    protected void onDestroy() {
        mQrCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate(){
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i("dan.y", "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQrCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("dan.y", "打开相机出错");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_flash:
                mQrCodeView.openFlashlight();
                break;
            case R.id.btn_close_flash:
                mQrCodeView.closeFlashlight();
                break;
            case R.id.btn_barcode:
                mQrCodeView.changeToScanBarcodeStyle();
                break;
            case R.id.btn_qrcode:
                mQrCodeView.changeToScanQRCodeStyle();
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
