package me.imstudio.core.ui.pager.listener;

import android.view.View;

public interface OnItemClickListener {

    void onItemClicked(View view, int position, boolean isSelected);
}