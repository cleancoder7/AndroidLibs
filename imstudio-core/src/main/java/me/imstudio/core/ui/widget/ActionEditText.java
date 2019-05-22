package me.imstudio.core.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.imstudio.core.R;

public final class ActionEditText extends EditText {

    private OnActionPerformClicked onActionPerformClicked;

    public ActionEditText(Context context) {
        super(context);
        init(context);
    }

    public ActionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Rect rect;
                        boolean isClickedInDrawableZone = false;
                        if (getResources().getBoolean(R.bool.is_right_to_left)) {
                            if (getCompoundDrawables()[DRAWABLE_LEFT] != null) {
                                rect = getCompoundDrawables()[DRAWABLE_LEFT].getBounds();
                                isClickedInDrawableZone = event.getRawX() <= rect.width() * 2;
                            }
                        } else {
                            if (getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                                rect = getCompoundDrawables()[DRAWABLE_RIGHT].getBounds();
                                isClickedInDrawableZone = event.getRawX() >= (getRight() - rect.width());
                            }
                        }
                        if (isClickedInDrawableZone) {
                            if (onActionPerformClicked != null)
                                onActionPerformClicked.onActionPerformClicked();
                            else
                                setText("");
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    public void setOnActionPerformClicked(OnActionPerformClicked onActionPerformClicked) {
        this.onActionPerformClicked = onActionPerformClicked;
    }

    public interface OnActionPerformClicked {
        void onActionPerformClicked();
    }
}
