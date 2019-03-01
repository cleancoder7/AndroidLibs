package com.imstudio.core.imageloader;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({TransformType.NORMAL, TransformType.CIRCLE, TransformType.FIT_CENTER})
public @interface TransformType {
    int NORMAL = 0;
    int CIRCLE = 1;
    int FIT_CENTER = 2;
}
