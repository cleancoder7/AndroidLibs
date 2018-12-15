package me.imstudio.core.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import me.imstudio.core.R;

public class ProgressBar extends android.widget.ProgressBar {

    public ProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressBar, 0, 0);
            int progressTint = array.getColor(R.styleable.ProgressBar_ims_progressTint, getResources().getColor(android.R.color.white));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable wrapDrawable = DrawableCompat.wrap(getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, progressTint);
                setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else
                getIndeterminateDrawable().setColorFilter(progressTint, PorterDuff.Mode.SRC_IN);
        }
    }
}
