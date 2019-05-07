package me.imstudio.core.ui.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.imstudio.core.ui.LayoutResId;
import me.imstudio.core.ui.pager.listener.OnItemClickListener;
import me.imstudio.core.utils.Utils;

/**
 * MultiSelectedRecyclerAdapter: used for Multi-Selected UI
 */
public abstract class MultiSelectedRecyclerAdapter<T extends Selectable,
        V extends MultiSelectedRecyclerAdapter.MultiSelectedHolderWrapper>
        extends RecyclerView.Adapter<V> implements OnItemClickListener {

    protected List<T> mData;                                // Data set
    protected LayoutInflater inflater = null;               // InFlat layout for row
    protected int mMaxSelected = 1;                         // Maximum item can be selected, DEFAULT = 1
    protected int mCurrentSelected = 0;                     // Number of current item was been selected
    protected OnItemClickListener onItemClickListener;      // Callback event when row was change

    protected MultiSelectedRecyclerAdapter() {
        this.mData = new ArrayList<>();
    }

    protected MultiSelectedRecyclerAdapter(Context context) {
        this();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
    }

    public MultiSelectedRecyclerAdapter(Context context, OnItemClickListener callBack) {
        this(context);
        this.onItemClickListener = callBack;
    }

    protected abstract int getLayout();

    protected View onCreateRowView(@NonNull ViewGroup viewGroup, int viewType) {
        try {
            if (getLayout() != LayoutResId.LAYOUT_NOT_DEFINED)
                return inflater.inflate(getLayout(), viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull V viewHolder, int position) {
        if (mData != null && position < mData.size()) {
            T item = mData.get(position);
            if (item != null) {
                viewHolder.setItem(item);
                viewHolder.setOnItemClickedListener(this);
                viewHolder.changeSelectedStatus(viewHolder.getItem().isSelected());
                viewHolder.bind(item);
            }
        }
    }

    public void setMaximumSelectedItem(int mMaxSelected) {
        if (mMaxSelected >= 0)
            this.mMaxSelected = mMaxSelected;
    }

    @Override
    public int getItemCount() {
        return this.mData != null ? this.mData.size() : 0;
    }

    public T get(int position) {
        return mData.get(position);
    }

    public void selectAll(boolean isSelected) {
        for (T item : mData)
            item.setSelected(isSelected);
        notifyDataSetChanged();
    }

    /**
     * Get all of items were selected
     */
    public List<T> getSelectedItems() {
        List<T> res = new ArrayList<>();
        for (T item : mData) {
            if (item.isSelected())
                res.add(item);
        }
        return res;
    }

    /**
     * set selected state for specify position
     */
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

    /**
     * Replace all of currents data by news list
     */
    public void replaceAll(List<T> data) {
        if (data == null || data.isEmpty())
            return;
        if (mData == null)
            mData = new ArrayList<>();
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * Append new item to items
     */
    public void add(T item) {
        if (item != null) {
            if (this.mData == null)
                this.mData = new ArrayList<>();
            if (!this.mData.contains(item))
                this.mData.add(item);
            notifyDataSetChanged();
        }
    }

    /**
     * Append new list of items
     */
    public void addAll(List<T> data) {
        if (data != null && !data.isEmpty()) {
            if (this.mData == null)
                this.mData = new ArrayList<>();
            for (T item : data) {
                if (!this.mData.contains(item))
                    this.mData.add(item);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * Clear all
     */
    public void clearAll() {
        if (this.mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Used for only 1 choice purpose or single select event
     */
    protected void fireSingleSelect(int position) {
        for (int i = 0; i < mData.size(); i++)
            mData.get(i).setSelected(i == position);
        notifyDataSetChanged();
        onItemClicked(null, position, false);
    }

    @Override
    public void onItemClicked(View view, int position, boolean isSelected) {
        if (this.onItemClickListener != null)
            this.onItemClickListener.onItemClicked(view, position, isSelected);
    }

    public class MultiSelectedHolderWrapper extends RecyclerView.ViewHolder {

        private T item;                                     // Current item
        private OnItemClickListener onItemClickListener;    // Callback event whenever item was change status

        public MultiSelectedHolderWrapper(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public void setOnItemClickedListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        protected void changeSelectedStatus(boolean isSelected) {
            setUISelected(isSelected);
            item.setSelected(isSelected);
        }

        /*
         *  Populate data on current view of row
         * */
        protected void bind(T item) {

        }

        /*
         * Set on click for view need to be selected
         * */
        protected void setOnClickListener() {

        }

        /*
         * How UI change when item was selected
         * */
        protected void setUISelected(boolean uiSelected) {

        }


        /*
         * Define an sample event when an item was selected
         * */
        protected final View.OnClickListener OnViewClickListenerEvent = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item == null)
                    return;
                // If now item getting selected status
                if (item.isSelected()) {
                    // if number of selected item is not 1
                    if (mCurrentSelected != 1 || mMaxSelected != 1) {
                        changeSelectedStatus(false);
                        mCurrentSelected--;
                        callBack(view, getAdapterPosition(), false);
                    }
                } else { // If now item not getting selected status
                    // If number of selected item is over than MAX-SELECTED
                    if (mCurrentSelected >= mMaxSelected) {
                        // Reset all to 1
                        if (mCurrentSelected == 1 && mMaxSelected == 1)
                            fireSingleSelect(getAdapterPosition());
                        return;
                    }
                    mCurrentSelected++;
                    changeSelectedStatus(true);
                    callBack(view, getAdapterPosition(), true);
                }
            }
        };

        private void callBack(View view, int position, boolean isSelected) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClicked(view, position, isSelected);
        }
    }


}
