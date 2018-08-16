package me.imstudio.core.ui.widgets;

import android.widget.ImageView;

public interface AnimatedToggleButtonEventListener {
    void onEvent(ImageView button, boolean buttonState);

    void onEventAnimationEnd(ImageView button, boolean buttonState);

    void onEventAnimationStart(ImageView button, boolean buttonState);
}
