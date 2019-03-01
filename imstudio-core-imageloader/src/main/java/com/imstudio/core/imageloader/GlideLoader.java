
package com.imstudio.core.imageloader;

import android.support.annotation.IntRange;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.diskCacheStrategyOf;
import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static com.bumptech.glide.request.RequestOptions.noAnimation;
import static com.bumptech.glide.request.RequestOptions.noTransformation;
import static com.bumptech.glide.request.RequestOptions.overrideOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;
import static com.bumptech.glide.request.RequestOptions.priorityOf;

public final class GlideLoader implements ImageLoaderInterface {

    @Override
    public <T> void load(ImageView imageView, T resource) {
        loadDefault(imageView, resource, R.drawable.ic_image_holder, TransformType.NORMAL);
    }

    @Override
    public <T> void load(ImageView imageView, T resource, @TransformType int transformType) {
        loadDefault(imageView, resource, R.drawable.ic_image_holder, transformType);
    }

    @Override
    public void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        for (Map.Entry<String, String> set : headers.entrySet())
            builder.addHeader(set.getKey(), set.getValue());
        GlideUrl glideUrl = new GlideUrl(url, builder.build());
        loadDefault(imageView, glideUrl, R.drawable.ic_image_holder, TransformType.NORMAL);
    }

    @Override
    public void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers, @TransformType int transformType) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        for (Map.Entry<String, String> set : headers.entrySet()) {
            builder.addHeader(set.getKey(), set.getValue());
        }
        GlideUrl glideUrl = new GlideUrl(url, builder.build());
        loadDefault(imageView, glideUrl, R.drawable.ic_image_holder, transformType);
    }

    @Override
    public <T> void loadDefault(ImageView imageView, T resource, int defaultDrawable, @TransformType int transformType) {
        switch (transformType) {
            case TransformType.NORMAL:
                Glide.with(imageView.getContext())
                        .load(resource)
                        .apply(noAnimation())
                        .apply(noTransformation())
                        .apply(placeholderOf(defaultDrawable))
                        .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .apply(priorityOf(Priority.IMMEDIATE))
                        .apply(errorOf(defaultDrawable))
                        .into(imageView);
                break;
            case TransformType.CIRCLE:
                Glide.with(imageView.getContext())
                        .load(resource)
                        .apply(placeholderOf(defaultDrawable))
                        .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .apply(priorityOf(Priority.IMMEDIATE))
                        .apply(errorOf(defaultDrawable))
                        .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop(), new CircleCrop())))
                        .into(imageView);
                break;
            case TransformType.FIT_CENTER:
                Glide.with(imageView.getContext())
                        .load(resource)
                        .apply(fitCenterTransform())
                        .apply(noAnimation())
                        .apply(noTransformation())
                        .apply(placeholderOf(defaultDrawable))
                        .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .apply(priorityOf(Priority.IMMEDIATE))
                        .apply(errorOf(defaultDrawable))
                        .into(imageView);
                break;
        }

    }

    @Override
    public <T> void loadDefaultOverride(ImageView imageView, T resource, int defaultDrawable,
                                        @IntRange(from = 0) int sizeOfOverride,
                                        @TransformType int transformType) {
        switch (transformType) {
            case TransformType.NORMAL:
                normalWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
                break;
            case TransformType.CIRCLE:
                circleWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
                break;
            case TransformType.FIT_CENTER:
                fitCenterWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
                break;
        }
    }

    @Override
    public <T> void normalWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride) {

        Glide.with(imageView.getContext())
                .load(resource)
                .apply(overrideOf(sizeOfOverride))
                .apply(noAnimation())
                .apply(noTransformation())
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .into(imageView);
    }

    @Override
    public <T> void circleWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride) {

        Glide.with(imageView.getContext())
                .load(resource)
                .apply(overrideOf(sizeOfOverride))
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop(), new CircleCrop())))
                .into(imageView);
    }

    @Override
    public <T> void fitCenterWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, @IntRange(from = 0) int sizeOfOverride) {

        Glide.with(imageView.getContext())
                .load(resource)
                .apply(overrideOf(sizeOfOverride))
                .apply(fitCenterTransform())
                .apply(noAnimation())
                .apply(noTransformation())
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .into(imageView);
    }

}

