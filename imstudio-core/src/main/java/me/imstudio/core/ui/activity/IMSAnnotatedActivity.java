package me.imstudio.core.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import me.imstudio.core.ui.LayoutResId;

public abstract class IMSAnnotatedActivity extends AppCompatActivity implements ActivityCommonInterface {

    private String TAG = IMSAnnotatedActivity.class.getSimpleName();
    protected static final String KEY_CLASS = "KEY_CLASS";
    protected Class className;

    protected int MAX_WAITING_TIME = 300;   // Mills
    private long mStartTime = 0L;

    @Override
    protected void onStart() {
        super.onStart();
        className = (Class) getIntent().getSerializableExtra(KEY_CLASS);
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        if (getClass().getAnnotation(LayoutResId.class) != null) {
            int layoutResId = getClass().getAnnotation(LayoutResId.class).layout();
            if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
                setContentView(layoutResId);
                onSyncViews(savedInstanceState);
                onSyncEvents();
                onSyncData();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboardIfNeed();
    }

    protected void setWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
    }

    protected void hideKeyboardIfNeed() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View v = getCurrentFocus();
            if (v == null || inputMethodManager == null)
                return;
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTAG() {
        return TAG;
    }

    protected boolean isLongEnough() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= MAX_WAITING_TIME;
        mStartTime = now;
        return res;
    }

}
