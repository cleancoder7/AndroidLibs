package me.imstudio.core.ui.pager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.imstudio.core.ui.pager.listener.OnItemClickListener;
import me.imstudio.core.utils.Utils;

public abstract class IMSRecyclerView<T extends Selectable>
        extends RecyclerView.Adapter<IMSRecyclerView.ViewHolder> implements OnItemClickListener {

    protected List<T> mData;
    protected LayoutInflater inflater = null;
    protected int mMaxSelected = 1;                    // Maximum items can be selected, default = 1
    protected int mCurrentSelected = 0;                // Current items be selected
    protected OnItemClickListener onItemClickListener;      // Callback event when row was clicked

    protected IMSRecyclerView() {
        this.mData = new ArrayList<>();
    }

    protected IMSRecyclerView(Context context) {
        this();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
    }

    public IMSRecyclerView(Context context, OnItemClickListener callBack) {
        this(context);
        this.onItemClickListener = callBack;
    }

    public void setMaxSelected(int mMaxSelected) {
        if (mMaxSelected >= 0)
            this.mMaxSelected = mMaxSelected;
    }

    // Set default for item will be selected
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

    // get all of items were selected
    public List<T> getSelectedItems() {
        List<T> res = new ArrayList<>();
        for (T item : mData) {
            if (item.isSelected())
                res.add(item);
        }
        return res;
    }

    // set selected state for specify position
    public void setSelected(int position) {
        if (!Utils.checkNotEmpty(mData) || position < 0 || position >= mData.size())
            return;
        // set unselected state for others
        for (int i = 0; i < mData.size(); i++)
            mData.get(i).setSelected(i == position);
        // Mark which item was selected
        mCurrentSelected = 1;
        notifyDataSetChanged();
    }

    // replace all of currents data by news list
    public void replaceAll(List<T> data) {
        if (data == null)
            return;
        if (mData == null)
            mData = new ArrayList<>();
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    // add new list of items
    public void addAll(List<T> data) {
        if (data != null && !data.isEmpty()) {
            if (this.mData == null)
                this.mData = new ArrayList<>();
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> data, boolean isDistinct) {
        if (data != null && !data.isEmpty()) {
            if (this.mData == null)
                this.mData = new ArrayList<>();
            if (isDistinct) {   // Check if distinct
                for (T item : data) {
                    // Check if item existed
                    if (!this.mData.contains(item))
                        this.mData.add(item);
                }
            } else
                this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    // Clear all
    public void clearAll() {
        if (this.mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    // Used for only 1 choice purpose or single select event
    protected void applyForSingleChoice(int position) {
        for (int i = 0; i < mData.size(); i++)
            mData.get(i).setSelected(i == position);
        notifyDataSetChanged();
        if (this.onItemClickListener != null)
            this.onItemClickListener.onClicked(position, false);
    }

    @Override
    public void onClicked(int position, boolean isSelected) {
        if (this.onItemClickListener != null)
            this.onItemClickListener.onClicked(position, isSelected);
    }

    public class ViewHolder<t extends Selectable> extends RecyclerView.ViewHolder {

        private t item;
        private OnItemClickListener onItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            setOnClickListener();
        }

        protected void setSelected(boolean isSelected) {
            setUISelected(isSelected);
            this.item.setSelected(isSelected);
        }

        protected t getItem() {
            return item;
        }

        protected void setItem(t item) {
            this.item = item;
        }

        protected void setCallBack(OnItemClickListener callBack) {
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
                        if (mCurrentSelected != 1 || mMaxSelected != 1) {
                            setSelected(false);
                            mCurrentSelected--;
                            if (onItemClick != null)
                                onItemClick.onClicked(getAdapterPosition(), false);
                        }
                    } else {
                        if (mCurrentSelected >= mMaxSelected) {
                            if (mCurrentSelected == 1 && mMaxSelected == 1)
                                applyForSingleChoice(getAdapterPosition());
                            return;
                        }
                        mCurrentSelected++;
                        setSelected(true);
                        if (onItemClick != null)
                            onItemClick.onClicked(getAdapterPosition(), true);
                    }
                }
            }
        };
    }

}
