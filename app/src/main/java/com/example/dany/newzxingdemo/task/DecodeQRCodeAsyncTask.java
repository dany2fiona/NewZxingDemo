package com.example.dany.newzxingdemo.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

/**
 * Created by dan.y on 2017/3/23.
 */

public class DecodeQRCodeAsyncTask extends AsyncTask<Bitmap,Void,String> {
    private Context mContext;
    private Listener mListener;

    public DecodeQRCodeAsyncTask(Context context,Listener listener){
        mContext = context;
        mListener = listener;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        return QRCodeDecoder.syncDecodeQRCode(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if (TextUtils.isEmpty(result)) {
            Toast.makeText(mContext, "未发现二维码", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
            //解析成功后的action
            mListener.onTaskSuccessed(result);
        }
    }

    public interface Listener{
        void onTaskSuccessed(String content);
    }

}
