package me.imstudio.core.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import me.imstudio.core.R;
import me.imstudio.core.utils.DimensionUtils;

public abstract class CoreDialog extends Dialog {

    protected Activity mOwnerActivity;

    public CoreDialog(Context context) {
        super(context, R.style.Theme_IMS_Dialog);
        mOwnerActivity = (context instanceof Activity) ? (Activity) context : null;
        if (mOwnerActivity != null)
            setOwnerActivity(mOwnerActivity);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mOwnerActivity = getOwnerActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayout());
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.windowAnimations = getWindowAnimations();
            params.width = getWidth();
            params.height = getHeight();
            window.setAttributes(params);
        }
    }

    protected int getWidth() {
        return DimensionUtils.getScreenWidth(mOwnerActivity) * 8 / 10;
    }

    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected abstract int getLayout();

    protected int getWindowAnimations() {
        return R.style.Theme_IMS_Dialog_Anim_Fade;
    }
}