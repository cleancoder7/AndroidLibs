package me.imstudio.core.ui.pager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class FadePageTransformer implements ViewPager.PageTransformer {
    @Override
    public final void transformPage(final View view, final float position) {
        final int pageWidth = view.getWidth();
        if (position < -0.999f) { // [-Infinity,-1)
            // This page is way off-screen to the left so hide it.
            view.setAlpha(0);
            view.setVisibility(View.GONE);
            view.setTranslationX(pageWidth);
        } else if (position <= 0.999f) { // (-1, 1)
            // The further the page is from being center page the more transparent it is.
            view.setAlpha(getAlpha(position));
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);
            // Make sure the page is visible
            view.setVisibility(View.VISIBLE);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right so hide it.
            view.setAlpha(0);
            view.setVisibility(View.GONE);
            view.setTranslationX(-pageWidth);
        }
    }

    private static float getAlpha(final float position) {
        return getFastQuadraticAlpha(position);
    }

    private static float getLinearAlpha(final float position) {
        if (position <= 0)
            return 1 + position;
        return 1 - position;
    }

    private static final float getFastQuadraticAlpha(final float position) {
        final float linearAlpha = getLinearAlpha(position);
        return linearAlpha * linearAlpha;
    }

    private static final float getSlowQuadraticAlpha(final float position) {
        return 1 - position * position;
    }
}