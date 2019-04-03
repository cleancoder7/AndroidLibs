package me.imstudio.core.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import me.imstudio.core.R;
import me.imstudio.core.utils.CompressUtils;
import me.imstudio.core.utils.FileUtils;

public class TextView extends android.support.v7.widget.AppCompatTextView {

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
    public void setText(CharSequence text, BufferType type) {
        super.setText(TextUtils.isEmpty(text) ? "" : Html.fromHtml(text.toString()), type);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // Built-in
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
    }
}
