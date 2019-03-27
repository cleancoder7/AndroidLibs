package me.imstudio.core.ui.widget.spinner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

import me.imstudio.core.R;
import me.imstudio.core.utils.CompressUtils;
import me.imstudio.core.utils.FileUtils;
import me.imstudio.core.utils.Utils;

public class Spinner extends android.support.v7.widget.AppCompatTextView {
    private OnNothingSelectedListener onNothingSelectedListener;
    private OnItemSelectedListener onItemSelectedListener;
    private SpinnerBaseAdapter adapter;
    private PopupWindow popupWindow;
    private ListView listView;
    private boolean hideArrow, nothingSelected;
    private int selectedIndex;
    private Drawable arrowDrawable;
    private int arrowColor, numberOfItems, textColor = -1;

    public Spinner(Context context) {
        super(context);
        init(context, null);
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void installFonts(Context context, AttributeSet attrs) {
        int style = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", Typeface.NORMAL);
        if (style == Typeface.BOLD) {
            if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                    getResources().getString(R.string.str_font_bold))) {
                setTypeface(Typeface.createFromFile(
                        CompressUtils.getDefaultFolderPath(context) +
                                getResources().getString(R.string.str_font_bold)));
            }
        } else if (style == Typeface.ITALIC) {
            if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                    getResources().getString(R.string.str_font_italic))) {
                setTypeface(Typeface.createFromFile(
                        CompressUtils.getDefaultFolderPath(context) +
                                getResources().getString(R.string.str_font_italic)));
            }
        } else {
            if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                    getResources().getString(R.string.str_font_regular))) {
                setTypeface(Typeface.createFromFile(
                        CompressUtils.getDefaultFolderPath(context) +
                                getResources().getString(R.string.str_font_regular)));
            }
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Spinner);
        boolean rtl = Utils.isRtl(context);
        try {
            installFonts(context, attrs);
            arrowColor = ta.getColor(R.styleable.Spinner_ims_arrowTint, Color.WHITE);
            textColor = ta.getColor(R.styleable.Spinner_ims_textColor, Color.DKGRAY);
            // Background
            setBackgroundResource(ta.getResourceId(R.styleable.Spinner_ims_backgroundDrawable, R.drawable.ms_background_default));
            hideArrow = ta.getBoolean(R.styleable.Spinner_ims_arrowHidden, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                arrowDrawable = ta.getDrawable(R.styleable.Spinner_ims_arrowDrawable);
            else
                arrowDrawable = Utils.getDrawable(context,
                        ta.getResourceId(R.styleable.Spinner_ims_arrowDrawable, R.drawable.ms_arrow_default));
        } finally {
            ta.recycle();
        }
        Resources resources = getResources();
        int left, right, bottom, top;
        left = right = bottom = top = resources.getDimensionPixelSize(R.dimen.margin_small);
        if (rtl)
            right = resources.getDimensionPixelSize(R.dimen.margin);
        else
            left = resources.getDimensionPixelSize(R.dimen.margin);
        setClickable(true);
        setPadding(left, top, right, bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && rtl) {
            setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        if (!hideArrow) {
            arrowDrawable = Utils.getDrawable(context, R.drawable.ms_arrow_default).mutate();
            arrowDrawable.setColorFilter(arrowColor, PorterDuff.Mode.SRC_IN);
            if (rtl)
                setCompoundDrawablesWithIntrinsicBounds(arrowDrawable, null, null, null);
            else
                setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null);
        }
        listView = new ListView(context);
        listView.setId(getId());
        listView.setDivider(null);
        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= selectedIndex && position < adapter.getCount())
                    position++;
                selectedIndex = position;
                nothingSelected = false;
                Object item = adapter.get(position);
                adapter.notifyItemSelected(position);
                setText(item.toString());
                collapse();
                if (onItemSelectedListener != null)
                    onItemSelectedListener.onItemSelected(Spinner.this, position, id, item);
            }
        });
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(16);
            popupWindow.setBackgroundDrawable(Utils.getDrawable(context, R.drawable.ms_drawable_default));
        } else
            popupWindow.setBackgroundDrawable(Utils.getDrawable(context, R.drawable.all_style_rect_white));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (nothingSelected && onNothingSelectedListener != null) {
                    onNothingSelectedListener.onNothingSelected(Spinner.this);
                }
                if (!hideArrow) {
                    animateArrow(false);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        popupWindow.setWidth(MeasureSpec.getSize(widthMeasureSpec));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!popupWindow.isShowing())
                expand();
            else
                collapse();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", super.onSaveInstanceState());
        bundle.putInt("selected_index", selectedIndex);
        if (popupWindow != null) {
            bundle.putBoolean("is_popup_showing", popupWindow.isShowing());
            collapse();
        } else {
            bundle.putBoolean("is_popup_showing", false);
        }
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;
            selectedIndex = bundle.getInt("selected_index");
            if (adapter != null) {
                setText(adapter.get(selectedIndex).toString());
                adapter.notifyItemSelected(selectedIndex);
            }
            if (bundle.getBoolean("is_popup_showing")) {
                if (popupWindow != null) {
                    // Post the show request into the looper to avoid bad token exception
                    post(new Runnable() {
                        @Override
                        public void run() {
                            expand();
                        }
                    });
                }
            }
            savedState = bundle.getParcelable("state");
        }
        super.onRestoreInstanceState(savedState);
    }

    /**
     * @return the selected item position
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Set the default spinner item using its index
     *
     * @param position the item's position
     */
    public void setSelectedIndex(int position) {
        if (adapter != null) {
            if (position >= 0 && position <= adapter.getCount()) {
                adapter.notifyItemSelected(position);
                selectedIndex = position;
                setText(adapter.get(position).toString());
            } else {
                throw new IllegalArgumentException("Position must be lower than adapter count!");
            }
        }
    }

    /**
     * Register a callback to be invoked when an item in the dropdown is selected.
     *
     * @param onItemSelectedListener The callback that will run
     */
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    /**
     * Register a callback to be invoked when the {@link PopupWindow} is shown but the user didn't select an item.
     *
     * @param onNothingSelectedListener the callback that will run
     */
    public void setOnNothingSelectedListener(@Nullable OnNothingSelectedListener onNothingSelectedListener) {
        this.onNothingSelectedListener = onNothingSelectedListener;
    }

    /**
     * Set the dropdown items
     *
     * @param items A list of items
     * @param <T>   The item type
     */
    public <T> void setItems(@NonNull List<T> items) {
        numberOfItems = items.size();
        adapter = new SpinnerAdapter<>(getContext(), items);
        setListItemTextColor(textColor);
        setAdapterInternal(adapter);
    }

    public void setListItemTextColor(int textColor) {
        if (textColor == -1)
            return;
        if (adapter != null)
            adapter.setTextColor(textColor);
    }

    /**
     * Set a custom adapter for the dropdown items
     *
     * @param adapter The list adapter
     */
    public void setAdapter(@NonNull ListAdapter adapter) {
        this.adapter = new SpinnerAdapterWrapper(getContext(), adapter);
        setAdapterInternal(this.adapter);
        setListItemTextColor(textColor);
    }

    public SpinnerBaseAdapter getAdapter() {
        return adapter;
    }

    private void setAdapterInternal(@NonNull SpinnerBaseAdapter adapter) {
        listView.setAdapter(adapter);
        if (selectedIndex >= numberOfItems) {
            selectedIndex = 0;
        }
        setText(adapter.get(selectedIndex).toString());
    }

    public int getSelectedItemPosition() {
        return listView.getSelectedItemPosition();
    }

    /**
     * Show the dropdown menu
     */
    private void expand() {
        if (!hideArrow) {
            animateArrow(true);
        }
        nothingSelected = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popupWindow.setOverlapAnchor(false);
            popupWindow.showAsDropDown(this);
        } else {
            int[] location = new int[2];
            getLocationOnScreen(location);
            int x = location[0];
            int y = getHeight() + location[1];
            popupWindow.showAtLocation(this, Gravity.TOP | Gravity.START, x, y);
        }
    }

    /**
     * Closes the dropdown menu
     */
    private void collapse() {
        if (!hideArrow)
            animateArrow(false);
        popupWindow.dismiss();
    }

    /**
     * Set the tint color for the dropdown arrow
     *
     * @param color the color value
     */
    public void setArrowColor(@ColorInt int color) {
        arrowColor = color;
        if (arrowDrawable != null) {
            arrowDrawable.setColorFilter(arrowColor, PorterDuff.Mode.SRC_IN);
        }
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(arrowDrawable, "level", start, end);
        animator.start();
    }

    /**
     * Interface definition for a callback to be invoked when an item in this view has been selected.
     *
     * @param <T> Adapter item type
     */
    public interface OnItemSelectedListener<T> {
        /**
         * <p>Callback method to be invoked when an item in this view has been selected. This callback is invoked only when
         * the newly selected position is different from the previously selected position or if there was no selected
         * item.</p>
         *
         * @param view     The {@link Spinner} view
         * @param position The position of the view in the adapter
         * @param id       The row id of the item that is selected
         * @param item     The selected item
         */
        void onItemSelected(Spinner view, int position, long id, T item);
    }

    /**
     * Interface definition for a callback to be invoked when the dropdown is dismissed and no item was selected.
     */
    interface OnNothingSelectedListener {
        /**
         * Callback method to be invoked when the {@link PopupWindow} is dismissed and no item was selected.
         *
         * @param spinner the {@link Spinner}
         */
        void onNothingSelected(Spinner spinner);
    }

}
