package me.imstudio.core.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import me.imstudio.core.R;
import me.imstudio.core.ui.widget.TextView;

public final class AlertDialog extends CoreDialog {

    private View.OnClickListener onNegativeClicked, onPositiveClicked;
    private TextView textTitle, textMessage, textPositive, textNegative;

    public AlertDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_alert;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        this.setTitle(title);
        this.setMessage(message);
        this.onPositiveClicked = onPositiveClicked;
        this.onNegativeClicked = onNegativeClicked;
    }

    public void show(String title, String message,
                     String positiveTitle, String negativeTitle,
                     View.OnClickListener onPositiveClicked, View.OnClickListener onNegativeClicked) {
        super.show();
        this.setTitle(title);
        this.setMessage(message);
        this.setTextPositive(positiveTitle);
        this.setTextNegative(negativeTitle);
        this.onPositiveClicked = onPositiveClicked;
        this.onNegativeClicked = onNegativeClicked;
    }

    private void setTextPositive(String positiveTitle) {
        if (TextUtils.isEmpty(positiveTitle))
            positiveTitle = "";
        this.textPositive.setText(positiveTitle);
    }

    private void setTextNegative(String negativeTitle) {
        if (TextUtils.isEmpty(negativeTitle))
            negativeTitle = "";
        this.textNegative.setText(negativeTitle);
    }

    private void setTitle(String title) {
        if (TextUtils.isEmpty(title))
            title = "";
        this.textTitle.setText(title);
    }

    private void setMessage(String message) {
        if (TextUtils.isEmpty(message))
            message = "";
        this.textMessage.setText(message);
    }
}