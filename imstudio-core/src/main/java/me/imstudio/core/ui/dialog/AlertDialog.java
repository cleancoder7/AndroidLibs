package me.imstudio.core.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.imstudio.core.R;
import me.imstudio.core.ui.widget.TextView;
import me.imstudio.core.utils.DimensionUtils;

public final class AlertDialog extends Dialog {

    private Activity mOwnerActivity;
    private View.OnClickListener onNegativeClicked, onPositiveClicked;
    private TextView textTitle, textMessage, textPositive, textNegative;

    public AlertDialog(Context context) {
        super(context, R.style.IMSTheme_Dialog);
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
        setContentView(R.layout.dialog_alert);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.windowAnimations = R.style.IMSTheme_AnimDialog_Fade;
            params.width = DimensionUtils.getScreenWidth(mOwnerActivity) * 8 / 10;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

        textTitle = findViewById(R.id.text_title);
        textMessage = findViewById(R.id.text_sub_title);
        textPositive = findViewById(R.id.button);
        textNegative = findViewById(R.id.button_cancel);

        textPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onPositiveClicked != null)
                    onPositiveClicked.onClick(v);
            }
        });
        textNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onNegativeClicked != null)
                    onNegativeClicked.onClick(v);
            }
        });
    }

    public void show(String title, String message) {
        show(title, message, null, null);
    }

    public void show(String title, String message, View.OnClickListener onPositiveClicked) {
        show(title, message, onPositiveClicked, null);
    }

    public void show(String title, String message, View.OnClickListener onPositiveClicked, View.OnClickListener onNegativeClicked) {
        super.show();
        this.textTitle.setText(title);
        this.textMessage.setText(message);
        this.onPositiveClicked = onPositiveClicked;
        this.onNegativeClicked = onNegativeClicked;
    }

    public void show(String title, String message,
                     String positiveTitle, String negativeTitle,
                     View.OnClickListener onPositiveClicked, View.OnClickListener onNegativeClicked) {
        super.show();
        this.textTitle.setText(title);
        this.textMessage.setText(message);
        this.textNegative.setText(negativeTitle);
        this.textPositive.setText(positiveTitle);
        this.onPositiveClicked = onPositiveClicked;
        this.onNegativeClicked = onNegativeClicked;
    }
}