package me.imstudio.core.ui.fragment;

import android.view.View;

public interface IIMSFragment {

    void onSyncViews(View rootView);

    void onSyncEvents();

    void onSyncData();

    int getLayout();
}
