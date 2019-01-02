package me.imstudio.core.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public abstract class IMSFragment extends Fragment implements IIMSFragment {

    protected static String TAG = IMSFragment.class.getSimpleName();
    protected int MAX_WAITING_TIME = 300;   // Mills

    protected View rootView;
    private Context mContext;
    private Activity mActivity;
    protected long mStartTime = 0L;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            this.mActivity = (Activity) context;
        else
            this.mContext = context;
    }

    @Override
    public Context getContext() {
        if (mContext != null)
            return mContext;
        if (mActivity == null)
            throw new NullPointerException();
        return mActivity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            this.mActivity = activity;
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

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardIfNeed();
    }

    public void hideKeyboardIfNeed() {
        if (mActivity == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = mActivity.getCurrentFocus();
        if (v == null || inputMethodManager == null)
            return;
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected void setWaitingTime(int maxWaitingTime) {
        this.MAX_WAITING_TIME = maxWaitingTime;
    }

    protected boolean isLongEnough() {
        long now = System.currentTimeMillis();
        boolean res = now - mStartTime >= MAX_WAITING_TIME;
        mStartTime = now;
        return res;
    }

}
