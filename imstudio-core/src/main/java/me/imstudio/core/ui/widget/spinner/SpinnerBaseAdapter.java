package me.imstudio.core.ui.widget.spinner;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import me.imstudio.core.R;

public abstract class SpinnerBaseAdapter<T> extends BaseAdapter {

    private final Context context;
    private int selectedIndex;
    private int textColor = Integer.MIN_VALUE;

    SpinnerBaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            textView = convertView.findViewById(R.id.text_view);
            if (textColor != Integer.MIN_VALUE)
                textView.setTextColor(textColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Configuration config = context.getResources().getConfiguration();
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                    textView.setTextDirection(View.TEXT_DIRECTION_RTL);
            }
            convertView.setTag(new ViewHolder(textView));
        } else
            textView = ((ViewHolder) convertView.getTag()).textView;

        String sFilter = getItem(position).toString();
        textView.setText(sFilter);

        return convertView;
    }

    int getSelectedIndex() {
        return selectedIndex;
    }

    public void notifyItemSelected(int index) {
        selectedIndex = index;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract T getItem(int position);

    @Override
    public abstract int getCount();

    public abstract void clearAll();

    public abstract T get(int position);

    public SpinnerBaseAdapter<T> setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    private static class ViewHolder {

        private TextView textView;

        private ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
