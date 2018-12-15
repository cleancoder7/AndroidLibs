package me.imstudio.core.ui.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabLayout extends android.support.design.widget.TabLayout {

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTypeface(String path) {
        try {
            ViewGroup parent = (ViewGroup) getChildAt(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                ViewGroup child = (ViewGroup) parent.getChildAt(i);
                for (int i1 = 0; i1 < child.getChildCount(); i1++) {
                    View view = child.getChildAt(i1);
                    if (view instanceof TextView)
                        ((TextView) view).setTypeface(Typeface.createFromFile(path));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTextAllCap(boolean isEnable) {
        try {
            ViewGroup parent = (ViewGroup) getChildAt(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                ViewGroup child = (ViewGroup) parent.getChildAt(i);
                for (int i1 = 0; i1 < child.getChildCount(); i1++) {
                    View view = child.getChildAt(i1);
                    if (view instanceof TextView)
                        ((TextView) view).setAllCaps(isEnable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTypeface(AssetManager manager, String path) {
        try {
            ViewGroup parent = (ViewGroup) getChildAt(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                ViewGroup child = (ViewGroup) parent.getChildAt(i);
                for (int i1 = 0; i1 < child.getChildCount(); i1++) {
                    View view = child.getChildAt(i1);
                    if (view instanceof TextView)
                        ((TextView) view).setTypeface(Typeface.createFromAsset(manager, path));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}