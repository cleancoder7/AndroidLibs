package com.imstudio.core.imageloader;

import android.widget.ImageView;

import java.util.LinkedHashMap;

public final class ImageLoader implements ImageLoaderInterface {

    private static ImageLoader imageLoader;

    private ImageLoaderInterface imageLoaderInterface;

    private ImageLoader() {

    }

    public ImageLoader setImageLoader(ImageLoaderInterface imageLoaderInterface) {
        this.imageLoaderInterface = imageLoaderInterface;
        return imageLoader;
    }

    public static ImageLoader plug() {
        if (imageLoader == null)
            imageLoader = new ImageLoader();
        return imageLoader;
    }

    private void setDefaultLoader() {
        this.imageLoaderInterface = new GlideLoader();
    }

    @Override
    public <T> void load(ImageView imageView, T resource) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.load(imageView, resource);
    }

    @Override
    public <T> void load(ImageView imageView, T resource, int transformType) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.load(imageView, resource, transformType);
    }

    @Override
    public void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.loadWithHeaders(imageView, url, headers);
    }

    @Override
    public void loadWithHeaders(ImageView imageView, String url, LinkedHashMap<String, String> headers, int transformType) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.loadWithHeaders(imageView, url, headers, transformType);
    }

    @Override
    public <T> void loadDefault(ImageView imageView, T resource, int defaultDrawable, int transformType) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.loadDefault(imageView, resource, defaultDrawable, transformType);
    }

    @Override
    public <T> void loadDefaultOverride(ImageView imageView, T resource, int defaultDrawable, int sizeOfOverride, int transformType) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.loadDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride, transformType);
    }

    @Override
    public <T> void normalWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, int sizeOfOverride) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.normalWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
    }

    @Override
    public <T> void circleWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, int sizeOfOverride) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.circleWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
    }

    @Override
    public <T> void fitCenterWithDefaultOverride(ImageView imageView, T resource, int defaultDrawable, int sizeOfOverride) {
        if (imageLoaderInterface == null)
            setDefaultLoader();
        this.imageLoaderInterface.fitCenterWithDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride);
    }
}