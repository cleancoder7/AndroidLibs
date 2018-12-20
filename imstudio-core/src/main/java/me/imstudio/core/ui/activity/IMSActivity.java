package me.imstudio.core.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class IMSActivity extends AppCompatActivity implements IIMSActivity {

    protected static String TAG = IMSActivity.class.getSimpleName();
    protected static final String KEY_CLASS = "KEY_CLASS";
    protected Class className;

    protected int MAX_WAITING_TIME = 600;   // Mills
    private long mStartTime = 0L;

    @Override
    protected void onStart() {
        super.onStart();
        className = (Class) getIntent().getSerializableExtra(KEY_CLASS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        onSyncViews();
        onSyncEvents();
        onSyncData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboardIfNeed();
    }

    protected void setMaxWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
    }

    protected void hideKeyboardIfNeed() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        if (v == null || inputMethodManager == null)
            return;
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected boolean isWaitingTime() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= MAX_WAITING_TIME;
        mStartTime = now;
        return res;
    }

}
