package eu.theappshop.baseadapter;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eu.theappshop.baseadapter.adapter.BaseAdapter;
import eu.theappshop.baseadapter.adapter.recyclerview.RecyclerViewAdapter;
import eu.theappshop.baseadapter.adapter.viewpager.ViewPagerAdapter;

public final class BaseBindingAdapters {

    @BindingAdapter("adapter")
    public static void bindPagerAdapter(RecyclerView recyclerView, BaseAdapter abstractAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(abstractAdapter));
    }

    @BindingAdapter("adapter")
    public static void bindPagerAdapter(ViewPager viewPager, BaseAdapter abstractAdapter) {
        viewPager.setAdapter(new ViewPagerAdapter(abstractAdapter));
    }
}
