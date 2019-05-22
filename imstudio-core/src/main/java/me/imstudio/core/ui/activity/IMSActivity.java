package me.imstudio.core.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.imstudio.core.ui.LayoutResId;
import me.imstudio.core.utils.KeyboardUtils;

public abstract class IMSActivity extends AppCompatActivity {

    private String TAG = IMSActivity.class.getSimpleName();
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
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        int layoutResId = getLayout();
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            setContentView(getLayout());
            onSyncViews(savedInstanceState);
            onSyncEvents();
            onSyncData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboardIfNeed();
    }

    protected void hideKeyboardIfNeed() {
        KeyboardUtils.hideKeyboardIfNeed(this);
    }

    protected int getLayout() {
        if (getClass().getAnnotation(LayoutResId.class) != null)
            return getClass().getAnnotation(LayoutResId.class).layout();
        return LayoutResId.LAYOUT_NOT_DEFINED;
    }

    protected abstract void onSyncViews(Bundle savedInstanceState);

    protected abstract void onSyncEvents();

    protected abstract void onSyncData();

    protected void setWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
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
