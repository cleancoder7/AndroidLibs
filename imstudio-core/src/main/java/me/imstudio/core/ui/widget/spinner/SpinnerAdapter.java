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
        try {
            return items != null ? items.size() - 1 : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void clearAll() {
        if (items != null)
            items.clear();
    }

    @Override
    public T getItem(int position) {
        try {
            return (position >= getSelectedIndex()) ? items.get(position + 1) : items.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T get(int position) {
        try {
            if (position < 0 || items == null ||
                    items.isEmpty() || position >= items.size())
                return null;
            return items.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}