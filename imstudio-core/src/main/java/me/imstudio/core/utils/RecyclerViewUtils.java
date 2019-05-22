package me.imstudio.core.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import me.imstudio.core.ui.pager.listener.OnItemClickListener;
import me.imstudio.core.ui.pager.listener.RecyclerItemClickListener;

/*
    RecyclerViewUtils: Helper class to build up with recycler view faster
*/
public class RecyclerViewUtils {

    private static final String TAG = RecyclerViewUtils.class.getSimpleName();

    private RecyclerView recyclerView;
    private static RecyclerViewUtils instance;

    private RecyclerViewUtils() {

    }

    @NonNull
    public static RecyclerViewUtils plug() {
        if (instance == null)
            synchronized (RecyclerViewUtils.class) {
                if (instance == null)
                    instance = new RecyclerViewUtils();
            }
        return instance;
    }

    public RecyclerViewUtils of(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }


    public RecyclerViewUtils setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (this.recyclerView != null)
            this.recyclerView.setLayoutManager(layoutManager);
        else
            errorMustBeCallOfFirst();
        return this;
    }

    public RecyclerViewUtils initializeWithDefaultLinearLayout(int orientation) {
        return initializeWithDefaults().setLinearLayout(orientation, false);
    }

    public RecyclerViewUtils initializeWithDefaultGridLayout(int spanCount) {
        return initializeWithDefaults().setLayoutManager(
                new GridLayoutManager(this.recyclerView.getContext(), spanCount));
    }

    public RecyclerViewUtils setLinearLayout(int orientation, boolean reverseLayout) {
        if (this.recyclerView != null)
            this.recyclerView.setLayoutManager(
                    new LinearLayoutManager(this.recyclerView.getContext(), orientation, reverseLayout));
        else
            errorMustBeCallOfFirst();
        return this;
    }

    public RecyclerViewUtils addItemDecoration(RecyclerView.ItemDecoration decor) {
        if (this.recyclerView != null)
            recyclerView.addItemDecoration(decor);
        else
            errorMustBeCallOfFirst();
        return this;
    }

    public RecyclerViewUtils addOnItemClickListener(Context context, OnItemClickListener onItemClickListener) {
        if (this.recyclerView != null)
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, onItemClickListener));
        else
            errorMustBeCallOfFirst();
        return this;
    }

    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
        if (recyclerView != null)
            this.recyclerView.setAdapter(adapter);
        else
            errorMustBeCallOfFirst();
    }

    public RecyclerViewUtils initializeWithDefaults() {
        if (recyclerView != null) {
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setNestedScrollingEnabled(false);
        } else
            errorMustBeCallOfFirst();
        return this;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density / 180);
    }

    private void errorMustBeCallOfFirst() {
        LogUtils.log(TAG, "--call \"of\" -- recycler view first");
    }
}
