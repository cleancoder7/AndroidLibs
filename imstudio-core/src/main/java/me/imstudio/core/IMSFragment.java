package me.imstudio.core;

import android.support.v4.app.Fragment;

public class IMSFragment extends Fragment {

    protected void onSyncData() {

    }

    protected void onSyncViews() {

    }

    protected void onSyncEvents() {

    }

    private long mStartTime = 0;

    protected boolean isEnoughTime() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= 600;
        mStartTime = now;
        return res;
    }

}
