package me.imstudio.core.ui.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import me.imstudio.core.R;

public class RecyclerStub extends FrameLayout {

    private View loadMore;
    private ViewStub viewStub;
    private RecyclerView recyclerView;

    public RecyclerStub(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RecyclerStub(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerStub(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_recycler_stub, this);
        recyclerView = findViewById(R.id.recycler_view);
        loadMore = findViewById(R.id.load_more);
        viewStub = findViewById(R.id.view_stub);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void showLoadMore() {
        if (loadMore != null)
            loadMore.setVisibility(VISIBLE);
    }

    public void hideLoadMore() {
        if (loadMore != null)
            loadMore.setVisibility(GONE);
    }

    public void notifyDataSetChanged() {
        if (recyclerView.getAdapter() != null) {
            viewStub.setVisibility(recyclerView.getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
            recyclerView.setVisibility(recyclerView.getAdapter().getItemCount() > 0 ? VISIBLE : GONE);
        }
    }
}