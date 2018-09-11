package me.imstudio.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.imstudio.core.R;

public class ToolBar extends RelativeLayout {

    private TextView tvTitle;
    private AppCompatImageView btnLeft;
    private AppCompatImageView btnRight;

    public ToolBar(Context context) {
        super(context);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Toolbar, 0, 0);
        try {
            int layout = array.getInteger(R.styleable.ToolBar_ims_layoutType, 0);
            int backgroundType = array.getInteger(R.styleable.ToolBar_ims_backgroundType, 0);
            inflate(context, layout == 0 ? R.layout.layout_tool_bar : R.layout.layout_tool_bar_lr, this);

            tvTitle = findViewById(R.id.text_view);
            btnLeft = findViewById(R.id.button_left);
            btnRight = findViewById(R.id.button_right);

            if (backgroundType == 0)
                getRootView().setBackgroundColor(array.getColor(R.styleable.ToolBar_ims_background,
                        getResources().getColor(android.R.color.transparent)));
            else
                getRootView().setBackgroundDrawable(array.getDrawable(R.styleable.ToolBar_ims_background));
            tvTitle.setTextColor(array.getColor(
                    R.styleable.ToolBar_ims_titleColor,
                    getResources().getColor(android.R.color.white)));

            String title = array.getString(R.styleable.ToolBar_ims_title);
            if (!TextUtils.isEmpty(title))
                setTitle(title);

            int l = array.getResourceId(R.styleable.ToolBar_ims_drawableLeft, -1);
            int r = array.getResourceId(R.styleable.ToolBar_ims_drawableRight, -1);
            if (l != -1)
                setDrawableLeft(l);
            if (r != -1)
                setDrawableRight(r);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    private void setTitle(String title) {
        tvTitle.setText(title);
        setBinding(Integer.MIN_VALUE, Integer.MIN_VALUE, null, null);
    }

    public void setTitle(String title, boolean isOnly) {
        if (isOnly)
            tvTitle.setText(title);
        else {
            setTitle(title);
        }
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        btnLeft.setOnClickListener(listener);
    }

    public void setOnRightClickListener(OnClickListener listener) {
        btnRight.setOnClickListener(listener);
    }

    private void setDrawableLeft(int icon) {
        if (icon == Integer.MIN_VALUE) {
            btnLeft.setVisibility(GONE);
        } else {
            btnLeft.setVisibility(VISIBLE);
            btnLeft.setImageResource(icon);
        }
    }

    private void setDrawableRight(int icon) {
        if (icon == Integer.MIN_VALUE) {
            btnRight.setVisibility(GONE);
        } else {
            btnRight.setVisibility(VISIBLE);
            btnRight.setImageResource(icon);
        }
    }

    public void setBinding(String title, int drawableLeft, OnClickListener leftListener) {
        tvTitle.setText(title);
        setOnlyLeft(drawableLeft, leftListener);
    }

    public void setBinding(int drawableLeft, int drawableRight,
                           OnClickListener leftListener, OnClickListener rightListener) {

        setOnLeftClickListener(leftListener);
        setOnRightClickListener(rightListener);
        setDrawableLeft(drawableLeft);
        setDrawableRight(drawableRight);
    }

    public void setOnlyLeft(int icon, @NonNull OnClickListener listener) {
        setDrawableLeft(icon);
        setOnLeftClickListener(listener);
        setDrawableRight(Integer.MIN_VALUE);
        setOnRightClickListener(null);
    }

}
