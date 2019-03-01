package com.imstudio.core.imageloader;

import android.support.annotation.IntRange;
import android.widget.ImageView;

import java.util.LinkedHashMap;

public interface ImageLoaderInterface {

    <T> void load(ImageView imageView, T resource);

    <T> void load(ImageView imageView, T resource, @TransformType int transformType);

    void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers);

    <T> void loadDefault(ImageView imageView, T resource, int defaultDrawable, @TransformType int transformType);
    
    void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers, @TransformType int transformType);

    <T> void normalWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride);

    <T> void circleWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride);

    <T> void fitCenterWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride);

    <T> void loadDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride, @TransformType int transformType);
}
