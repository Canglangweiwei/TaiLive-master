package com.zanlabs.infinitevpager;

import android.view.View;
import android.view.ViewGroup;

public abstract class InfinitePagerAdapter extends RecyclingPagerAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }

    /**
     * Note: use getItemCount instead
     */
    @Override
    public final int getCount() {
        return getItemCount() * InfiniteViewPager.FakePositionHelper.MULTIPLIER;
    }

    @Deprecated

    protected View getViewInternal(int position, View convertView, ViewGroup container) {
        if (getItemCount() == 0)
            return null;
        return getView(position % getItemCount(), convertView, container);
    }

    public abstract int getItemCount();
}
