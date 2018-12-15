package me.imstudio.core.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import me.imstudio.core.R;
import me.imstudio.core.utils.CompressUtils;
import me.imstudio.core.utils.FileUtils;

public class Button extends android.support.v7.widget.AppCompatButton {

    public Button(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(TextUtils.isEmpty(text) ? "" : Html.fromHtml(text.toString()), type);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            int style = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", Typeface.NORMAL);
            if (style == Typeface.BOLD) {
                if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_bold))) {
                    super.setTypeface(Typeface.createFromFile(
                            CompressUtils.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_bold)));
                }
            } else if (style == Typeface.ITALIC) {
                if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_italic))) {
                    super.setTypeface(Typeface.createFromFile(
                            CompressUtils.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_italic)));
                }
            } else {
                if (FileUtils.isExist(CompressUtils.getDefaultFolderPath(context) +
                        getResources().getString(R.string.str_font_regular))) {
                    super.setTypeface(Typeface.createFromFile(
                            CompressUtils.getDefaultFolderPath(context) +
                                    getResources().getString(R.string.str_font_regular)));
                }
            }
        }
    }
}
