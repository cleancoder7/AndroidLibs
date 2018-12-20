package me.imstudio.core.ui.widget.spinner;

import android.content.Context;

import java.util.List;

public class SpinnerAdapter<T> extends SpinnerBaseAdapter {

    private final List<T> items;

    public SpinnerAdapter(Context context, List<T> items) {
        super(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size() - 1;
    }

    @Override
    public T getItem(int position) {
        return (position >= getSelectedIndex()) ? items.get(position + 1) : items.get(position);
    }

    @Override
    public T get(int position) {
        return items.get(position);
    }

}