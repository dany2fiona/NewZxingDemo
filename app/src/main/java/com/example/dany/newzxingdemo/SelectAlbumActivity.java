package com.example.dany.newzxingdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dany.newzxingdemo.task.DecodeQRCodeAsyncTask;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class SelectAlbumActivity extends AppCompatActivity implements View.OnClickListener,DecodeQRCodeAsyncTask.Listener{
    private static final int ALBUM_RESULTCODE = 0x1003;
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private LinearLayout mToolbarbackLl;
    private Button mPickQrcodeBtn;
    private TextView mQrcodeTv;
    private DecodeQRCodeAsyncTask mDecodeQRCodeAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_album);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mToolbarbackLl = (LinearLayout) findViewById(R.id.toolbar_back);
        mToolbarbackLl.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        mTitleTv.setText("从图库选择二维码");
        mPickQrcodeBtn = (Button) findViewById(R.id.btn_pick_qrcode);
        mPickQrcodeBtn.setOnClickListener(this);
        mQrcodeTv = (TextView) findViewById(R.id.tv_qrcode);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pick_qrcode:
                mQrcodeTv.setText("");
                gotoGallery();
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    private void gotoGallery(){
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM_RESULTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == ALBUM_RESULTCODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            mDecodeQRCodeAsyncTask = new DecodeQRCodeAsyncTask(SelectAlbumActivity.this,this);
            mDecodeQRCodeAsyncTask.execute(QRCodeDecoder.getDecodeAbleBitmap(imagePath));
            c.close();
        }
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
        mQrcodeTv.setText(content);
    }
}
