package me.imstudio.core.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;

public final class ThemeUtils {
    /**
     * Get a resource id from a resource styled according to the the context's theme.
     */
    public static int resolveResourceIdFromAttr(Context context, @AttrRes int attr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        int attributeResourceId = a.getResourceId(0, 0);
        a.recycle();
        return attributeResourceId;
    }
}
