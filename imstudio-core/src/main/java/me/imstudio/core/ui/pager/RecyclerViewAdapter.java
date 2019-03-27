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

public abstract class RecyclerViewAdapter<D, V extends ViewHolderWrapper<D>> extends
        RecyclerView.Adapter<V> {

    protected List<D> data;
    protected LayoutInflater inflater = null;

    public RecyclerViewAdapter() {
        data = new ArrayList<>();
    }

    public RecyclerViewAdapter(Context context) {
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
        final D item = data.get(position);
        if (item != null)
            viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void add(D item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public List<D> getData() {
        return data;
    }

    public D get(int position) {
        try {
            return data.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAll(Collection<D> collection) {
        data.addAll(collection);
        notifyDataSetChanged();
    }

    public void replaceAll(Collection<D> collection) {
        if (data != null) {
            data.clear();
            data.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
}
