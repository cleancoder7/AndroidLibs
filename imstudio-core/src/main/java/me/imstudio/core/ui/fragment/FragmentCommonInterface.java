package me.imstudio.core.ui.fragment;

import android.view.View;

public interface FragmentCommonInterface {

    void onSyncViews(View rootView);

    void onSyncEvents();

    void onSyncData();
}
