package me.imstudio.core.ui.pager.listener;

import android.view.View;

public interface OnItemClickListener {

    void onItemSelected(int position, boolean isSelected);

    void onItemClicked(View view, int position);
}