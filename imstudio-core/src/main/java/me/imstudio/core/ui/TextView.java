package me.imstudio.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import me.imstudio.core.R;
import me.imstudio.core.util.CompressUtil;
import me.imstudio.core.util.FileUtil;

public class TextView extends android.support.v7.widget.AppCompatTextView {

    private Paint paint;
    private boolean isStroke;

    public TextView(Context context) {
        super(context);
        init(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStroke && paint != null)
            canvas.drawLine(0, getMeasuredHeight() / 2, getMeasuredWidth(),
                    getMeasuredHeight() / 2, paint);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(TextUtils.isEmpty(text) ? "" : Html.fromHtml(text.toString()), type);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // Built-in
            int style = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", Typeface.NORMAL);
            if (style == Typeface.BOLD) {
                if (FileUtil.isExist(CompressUtil.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_bold))) {
                    setTypeface(Typeface.createFromFile(
                            CompressUtil.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_bold)));
                }
            } else if (style == Typeface.ITALIC) {
                if (FileUtil.isExist(CompressUtil.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_italic))) {
                    setTypeface(Typeface.createFromFile(
                            CompressUtil.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_italic)));
                }
            } else {
                if (FileUtil.isExist(CompressUtil.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_regular))) {
                    setTypeface(Typeface.createFromFile(
                            CompressUtil.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_regular)));
                }
            }
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextView, 0, 0);
            isStroke = array.getBoolean(R.styleable.TextView_ims_isStroke, false);
            if (isStroke) {
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(getResources().getDisplayMetrics().density * 1);
            }
        }
    }
}
