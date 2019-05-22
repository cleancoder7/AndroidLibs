package me.imstudio.core.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.imstudio.core.ui.LayoutResId;
import me.imstudio.core.utils.KeyboardUtils;

public abstract class IMSFragment extends Fragment {

    private String TAG = IMSFragment.class.getSimpleName();
    protected int MAX_WAITING_TIME = 300;   // Mills

    protected View rootView;
    protected long mStartTime = 0L;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTAG(this.getClass().getSimpleName());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResId = getLayout();
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            rootView = inflater.inflate(layoutResId, container, false);
            onSyncViews(rootView);
            onSyncEvents();
            onSyncData();
        }
        return rootView;
    }

    protected int getLayout() {
        if (getClass().getAnnotation(LayoutResId.class) != null)
            return getClass().getAnnotation(LayoutResId.class).layout();
        return LayoutResId.LAYOUT_NOT_DEFINED;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardIfNeed();
    }

    protected void setTAG(String tag) {
        this.TAG = tag;
    }

    public String getTAG() {
        return TAG;
    }

    protected void hideKeyboardIfNeed() {
        KeyboardUtils.hideKeyboardIfNeed(requireActivity());
    }

    protected void setWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
    }

    protected boolean isLongEnoughFromLastTime() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= MAX_WAITING_TIME;
        mStartTime = now;
        return res;
    }

    protected abstract void onSyncViews(View rootView);

    protected abstract void onSyncEvents();

    protected abstract void onSyncData();

}
