package me.imstudio.core.ui.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import me.imstudio.core.ui.fragment.IMSFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<IMSFragment> mFragmentList;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mFragmentList = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(int key, IMSFragment fragment) {
        mFragmentList.put(key, fragment);
        notifyDataSetChanged();
    }

    public void replaceAll(SparseArray<IMSFragment> array) {
        mFragmentList = array;
        notifyDataSetChanged();
    }
}