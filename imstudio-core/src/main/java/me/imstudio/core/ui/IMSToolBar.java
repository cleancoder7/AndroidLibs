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

public class IMSToolBar extends RelativeLayout {

    private TextView tvTitle;
    private AppCompatImageView buttonLeft, buttonRight;

    public IMSToolBar(Context context) {
        super(context);
        init(context, null);
    }

    public IMSToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IMSToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IMSToolBar);
        try {
            int layout = array.getInteger(R.styleable.IMSToolBar_ims_layoutType, 0);
            inflate(context, layout == 0 ? R.layout.layout_tool_bar : R.layout.layout_tool_bar_lr, this);
            if (array.hasValue(R.styleable.IMSToolBar_ims_background)) {
                setBackgroundColor(array.getColor(R.styleable.IMSToolBar_ims_background,
                        getResources().getColor(android.R.color.transparent)));
            }
            tvTitle = findViewById(R.id.text_view);
            buttonLeft = findViewById(R.id.button_left);
            buttonRight = findViewById(R.id.button_right);
            tvTitle.setTextColor(array.getColor(R.styleable.IMSToolBar_ims_titleColor, getResources().getColor(android.R.color.white)));
            String title = array.getString(R.styleable.IMSToolBar_ims_title);
            if (!TextUtils.isEmpty(title))
                setTitle(title);
            int l = array.getResourceId(R.styleable.IMSToolBar_ims_drawableLeft, -1);
            int r = array.getResourceId(R.styleable.IMSToolBar_ims_drawableRight, -1);
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

    public void setTitle(String title) {
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
        buttonLeft.setOnClickListener(listener);
    }

    public void setOnRightClickListener(OnClickListener listener) {
        buttonRight.setOnClickListener(listener);
    }

    private void setDrawableLeft(int icon) {
        if (icon == Integer.MIN_VALUE) {
            buttonLeft.setVisibility(GONE);
        } else {
            buttonLeft.setVisibility(VISIBLE);
            buttonLeft.setImageResource(icon);
        }
    }

    private void setDrawableRight(int icon) {
        if (icon == Integer.MIN_VALUE) {
            buttonRight.setVisibility(GONE);
        } else {
            buttonRight.setVisibility(VISIBLE);
            buttonRight.setImageResource(icon);
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
