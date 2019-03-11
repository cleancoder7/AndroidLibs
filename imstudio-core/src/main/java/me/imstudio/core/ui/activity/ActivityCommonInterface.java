package me.imstudio.core.ui.activity;

import android.os.Bundle;

public interface ActivityCommonInterface {

    void onSyncViews(Bundle savedInstanceState);

    void onSyncEvents();

    void onSyncData();
}
