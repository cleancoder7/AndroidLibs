package me.imstudio.core.ui.pager.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mSpace;
        outRect.bottom = mSpace;
        outRect.left = mSpace;
        outRect.right = mSpace;
    }
}
