package me.imstudio.core.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class IMSFragment extends Fragment implements IIMSFragment {

    protected static String TAG = IMSFragment.class.getSimpleName();
    protected int MAX_WAITING_TIME = 600;   // Mills

    protected View rootView;
    protected Context mContext;
    protected long mStartTime = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        rootView = inflater.inflate(getLayout(), container, false);
        onSyncViews(rootView);
        onSyncEvents();
        onSyncData();
        return rootView;
    }

    protected void setMaxWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
    }

    protected boolean isWaitingTime() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= MAX_WAITING_TIME;
        mStartTime = now;
        return res;
    }

}
