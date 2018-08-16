package me.imstudio.core.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import me.imstudio.core.R;

public class IndicatorLayout extends LinearLayout {

    private static final int MIN_DEFAULT_INDICATOR = 1;
    private int orientation, iconSize, margin, numberOfIndicator = MIN_DEFAULT_INDICATOR;
    private Drawable selectedDrawable = null, unSelectedDrawable = null;

    public IndicatorLayout(Context context) {
        super(context);
        init(context, null);
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndicatorLayout, 0, 0);
            numberOfIndicator = array.getInt(R.styleable.IndicatorLayout_ims_number, MIN_DEFAULT_INDICATOR);
            iconSize = (int) array.getDimension(R.styleable.IndicatorLayout_ims_indicatorSize, 0);
            margin = (int) array.getDimension(R.styleable.IndicatorLayout_ims_margin, 0);
            selectedDrawable = array.getDrawable(R.styleable.IndicatorLayout_ims_selectedBackground);
            unSelectedDrawable = array.getDrawable(R.styleable.IndicatorLayout_ims_unSelectedBackground);
            orientation = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "orientation", HORIZONTAL);
            render(context);
        }
    }

    private void render(Context context) {
        removeAllViews();
        if (numberOfIndicator > MIN_DEFAULT_INDICATOR)
            for (int i = 0; i < numberOfIndicator; i++) {
                View view = new View(context);
                setIndicatorSelected(view, i == 0);
                LayoutParams layoutParams = new LayoutParams(iconSize, iconSize);
                if (orientation == VERTICAL)
                    layoutParams.setMargins(0, margin, 0, margin);
                else
                    layoutParams.setMargins(margin, 0, margin, 0);
                view.setLayoutParams(layoutParams);
                addView(view);
            }
    }

    private void setIndicatorSelected(View view, boolean isSelected) {
        if (isSelected) {
            if (selectedDrawable == null)
                selectedDrawable = getResources().getDrawable(R.drawable.ic_indicator_selected);
            view.setBackgroundDrawable(selectedDrawable);
        } else {
            if (unSelectedDrawable == null)
                unSelectedDrawable = getResources().getDrawable(R.drawable.ic_indicator_unselected);
            view.setBackgroundDrawable(unSelectedDrawable);
        }
    }

    public void setNumberOfIndicator(int numberOfIndicator) {
        setVisibility(numberOfIndicator <= MIN_DEFAULT_INDICATOR ? GONE : VISIBLE);
        this.numberOfIndicator = numberOfIndicator;
        render(getContext());
    }

    public void setSelectIndex(int index) {
        for (int i = 0; i < getChildCount(); i++)
            setIndicatorSelected(getChildAt(i), i == index);
    }
}