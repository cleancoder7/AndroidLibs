package me.imstudio.core.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import me.imstudio.core.R;
import me.imstudio.core.utils.CompressUtils;
import me.imstudio.core.utils.FileUtils;

public class EditText extends TextInputEditText {

    public EditText(Context context) {
        super(context);
        init(context, null);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            if (attrs != null) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
