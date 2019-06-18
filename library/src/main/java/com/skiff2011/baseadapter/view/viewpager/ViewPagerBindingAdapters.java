package com.skiff2011.baseadapter.view.viewpager;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.ItemAdapter;

public class ViewPagerBindingAdapters {

  @BindingAdapter("adapter")
  public static void _bindAdapter(final ViewPager viewPager,
      @Nullable final ItemAdapter adapter) {
    //if adapter instance was changed while viewpager was attached to screen
    // we have to unsubscribe the previous adapter
    PagerAdapter prevAdapter = viewPager.getAdapter();
    if (adapter instanceof AdapterDataObserver && prevAdapter instanceof AdapterDataObserver) {
      adapter.unregisterObserver((AdapterDataObserver) prevAdapter);
    }
    if (adapter == null) {
      viewPager.setAdapter(null);
      return;
    }
    final ViewPagerAdapter decorator = new ViewPagerAdapter(adapter);
    viewPager.setAdapter(decorator);
    //if adapter instance was changed while viewpager was attached to screen
    //we have register observer since view is already attached to window
    if (viewPager.getWindowToken() != null) {
      adapter.registerObserver(decorator);
    }
    viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
      @Override
      public void onViewAttachedToWindow(View v) {
        adapter.registerObserver(decorator);
      }

      @Override
      public void onViewDetachedFromWindow(View v) {
        adapter.unregisterObserver(decorator);
      }
    });
  }

  @BindingAdapter("adapter")
  public static void _bindAdapter(final TabLayout tabLayout,
      @Nullable final AbstractItemAdapter adapter) {
    Object prevAdapter = tabLayout.getTag();
    if (adapter instanceof AdapterDataObserver && prevAdapter instanceof AdapterDataObserver) {
      adapter.unregisterObserver((AdapterDataObserver) prevAdapter);
    }
    if (adapter == null) {
      //Although this method is deprecated it does exactly what we need (populating new data when pager adapter changes)
      tabLayout.setTabsFromPagerAdapter(null);
      return;
    }
    final ViewPagerAdapter decorator = new ViewPagerAdapter(adapter);
    //Attention you must update tabLayout on viewpager scroll by yourself
    tabLayout.setTabsFromPagerAdapter(decorator);
    if (tabLayout.getWindowToken() != null) {
      adapter.registerObserver(decorator);
    }
    tabLayout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
      @Override
      public void onViewAttachedToWindow(View v) {
        adapter.registerObserver(decorator);
      }

      @Override
      public void onViewDetachedFromWindow(View v) {
        adapter.unregisterObserver(decorator);
      }
    });
  }
}
