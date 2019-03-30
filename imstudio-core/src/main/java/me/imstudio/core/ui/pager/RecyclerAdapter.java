package me.imstudio.core.ui.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.imstudio.core.ui.LayoutResId;

public abstract class RecyclerAdapter<D, V extends ViewHolderWrapper<D>> extends
        RecyclerView.Adapter<V> {

    protected List<D> mData;
    protected LayoutInflater inflater = null;

    public RecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public RecyclerAdapter(Context context) {
        this();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
    }

    protected abstract int getLayout();

    protected View onCreateRowView(@NonNull ViewGroup viewGroup, int viewType) {
        try {
            if (getLayout() != LayoutResId.LAYOUT_NOT_DEFINED)
                return inflater.inflate(getLayout(), viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull V viewHolder, int position) {
        final D item = mData.get(position);
        if (item != null)
            viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void add(D item) {
        if (!mData.contains(item)) {
            mData.add(item);
            notifyDataSetChanged();
        }

    }

    public List<D> getData() {
        return mData;
    }

    public D get(int position) {
        try {
            return mData.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAll(Collection<D> data) {
        for (D item : data) {
            if (!mData.contains(item))
                mData.add(item);
        }
        notifyDataSetChanged();
    }

    public void replaceAll(Collection<D> collection) {
        if (mData != null) {
            mData.clear();
            mData.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        mData.clear();
        notifyDataSetChanged();
    }
}
