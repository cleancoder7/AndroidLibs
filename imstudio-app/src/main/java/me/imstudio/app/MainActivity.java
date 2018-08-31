package me.imstudio.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bangdev.wifichua.util.AESCryptor;

import me.imstudio.core.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.log(TAG, "secretKey: " + AESCryptor.getSecretKey());
        LogUtil.log(TAG, "QRKey: " + AESCryptor.getQRKey());
        LogUtil.log(TAG, "JWTKey: " + AESCryptor.getJWTKey());
        LogUtil.log(TAG, "hotspotKey: " + AESCryptor.getHotspotKey());
    }
}
