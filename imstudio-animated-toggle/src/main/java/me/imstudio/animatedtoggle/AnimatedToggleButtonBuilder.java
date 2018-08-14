package me.imstudio.animatedtoggle;

import android.content.Context;

import me.imstudio.core.Utils;

public class AnimatedToggleButtonBuilder {

    private AnimatedToggleButton sparkButton;
    private Context context;

    public AnimatedToggleButtonBuilder(Context context) {
        this.context = context;
        sparkButton = new AnimatedToggleButton(context);
    }

    public AnimatedToggleButtonBuilder setActiveImage(int resourceId) {
        sparkButton.imageResourceIdActive = resourceId;
        return this;
    }

    public AnimatedToggleButtonBuilder setInactiveImage(int resourceId) {
        sparkButton.imageResourceIdInactive = resourceId;
        return this;
    }

    public AnimatedToggleButtonBuilder setPrimaryColor(int color) {
        sparkButton.primaryColor = color;
        return this;
    }

    public AnimatedToggleButtonBuilder setSecondaryColor(int color) {
        sparkButton.secondaryColor = color;
        return this;
    }

    public AnimatedToggleButtonBuilder setImageSizePx(int px) {
        sparkButton.imageSize = px;
        return this;
    }

    public AnimatedToggleButtonBuilder setImageSizeDp(int dp) {
        sparkButton.imageSize = Utils.dpToPx(context, dp);
        return this;
    }

    public AnimatedToggleButtonBuilder setAnimationSpeed(float value) {
        sparkButton.animationSpeed = value;
        return this;
    }

    public AnimatedToggleButton build() {
        sparkButton.init();
        return sparkButton;
    }
}
