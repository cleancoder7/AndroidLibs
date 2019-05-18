package me.imstudio.core.ui.pager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolderWrapper<D> extends RecyclerView.ViewHolder {

    public ViewHolderWrapper(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(D item, int position);

}