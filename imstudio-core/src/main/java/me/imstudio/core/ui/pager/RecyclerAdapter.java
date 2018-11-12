package me.imstudio.core.ui.pager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerAdapter<T extends Selectable>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements OnItemClick {

    protected List<T> mData;
    protected LayoutInflater inflater;
    protected int mMax = 1, mCount = 0;
    protected OnItemClick onItemClick;

    protected RecyclerAdapter() {
        mData = new ArrayList<>();
    }

    protected RecyclerAdapter(Context context) {
        this();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
    }

    public RecyclerAdapter(Context context, OnItemClick callBack) {
        this(context);
        this.onItemClick = callBack;
    }

    protected T setSelectable(ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            T item = mData.get(position);
            if (item != null) {
                holder.setItem(item);
                holder.setCallBack(this);
                holder.setSelected(holder.getItem().isSelected());
            }
            return item;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (this.mData == null)
            return 0;
        return this.mData.size();
    }

    public T get(int position) {
        return mData.get(position);
    }

    public List<T> getSelected() {
        List<T> res = new ArrayList<>();
        for (T item : mData) {
            if (item.isSelected())
                res.add(item);
        }
        return res;
    }

    public void setSelected(int position) {
        if (position < 0 || position >= mData.size())
            return;
        for (int i = 0; i < mData.size(); i++)
            mData.get(i).setSelected(i == position);
        mCount = 1;
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> data) {
        if (data == null)
            return;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    protected void applyForSingleChoice(int position) {
        for (int i = 0; i < mData.size(); i++)
            mData.get(i).setSelected(i == position);
        notifyDataSetChanged();
        if (this.onItemClick != null)
            this.onItemClick.onClicked(position, false);
    }

    @Override
    public void onClicked(int position, boolean isSelected) {

    }

    public class ViewHolder<T extends Selectable> extends RecyclerView.ViewHolder {

        private T item;
        private OnItemClick onItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            setOnClickListener();
        }

        protected void setSelected(boolean isSelected) {
            setUISelected(isSelected);
            item.setSelected(isSelected);
        }

        protected T getItem() {
            return item;
        }

        protected void setItem(T item) {
            this.item = item;
        }

        protected void setCallBack(OnItemClick callBack) {
            this.onItemClick = callBack;
        }

        protected void setOnClickListener() {
            itemView.setOnClickListener(onClickListener);
        }

        protected void setUISelected(boolean isSelected) {
            itemView.setSelected(isSelected);
        }

        protected final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item != null) {
                    if (item.isSelected()) {
                        if (mCount != 1 || mMax != 1) {
                            setSelected(false);
                            mCount--;
                            if (onItemClick != null)
                                onItemClick.onClicked(getAdapterPosition(), false);
                        }
                    } else {
                        if (mCount >= mMax) {
                            if (mCount == 1 && mMax == 1)
                                applyForSingleChoice(getAdapterPosition());
                            return;
                        }
                        mCount++;
                        setSelected(true);
                        if (onItemClick != null)
                            onItemClick.onClicked(getAdapterPosition(), true);
                    }
                }
            }
        };
    }

}
