package me.imstudio.core;

import android.support.v7.app.AppCompatActivity;

public class IMSActivity extends AppCompatActivity implements IMSActivityInterface {

    protected void enableSystemBarTintManager() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            IMSSystemBar tintManager = new IMSSystemBar(this);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    public void onSyncViews() {

    }

    @Override
    public void onSyncEvents() {

    }

    @Override
    public void onSyncData() {

    }
}
