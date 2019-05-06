package com.skiff2011.baseadapter.view;

import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.misc.LayoutManagerType;
import com.skiff2011.baseadapter.vm.SpannedVM;
import com.skiff2011.baseadapter.vm.VM;

public final class BaseBindingAdapters {

  @BindingAdapter(value = {
      "adapter", "layoutManager", "reverse", "spanCount", "orientation"
  }, requireAll = false)
  public static void _bindAdapter(final RecyclerView recyclerView,
      @Nullable final AbstractVmAdapter adapter,
      LayoutManagerType layoutManagerType,
      boolean reverse,
      int spanCount,
      Integer orientation) {

    final RecyclerViewAdapter decorator = adapter == null ? null : new RecyclerViewAdapter(adapter);

    recyclerView.setAdapter(decorator);

    if (orientation == null) {
      orientation = LinearLayout.VERTICAL;
    }

    RecyclerView.LayoutManager lm;
    switch (layoutManagerType == null ? LayoutManagerType.LINEAR : layoutManagerType) {
      case LINEAR:
        lm = new LinearLayoutManager(recyclerView.getContext(), orientation, reverse);
        break;
      case GRID:
        lm = new GridLayoutManager(recyclerView.getContext(), spanCount, orientation, reverse);
        break;
      case STAGGERED_GRID:
        lm = new StaggeredGridLayoutManager(spanCount, orientation);
        break;
      case VARIABLE_GRID:
        GridLayoutManager glm =
            new GridLayoutManager(recyclerView.getContext(), SpannedVM.MAX_SPAN_SIZE);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
          @Override
          public int getSpanSize(int position) {
            VM vm = adapter.get(position);
            if (!(vm instanceof SpannedVM)) {
              throw new IllegalStateException(
                  "VMs inside variable span grid should implement SpannedVM");
            }
            return ((SpannedVM) vm).getSpanSize();
          }
        });
        lm = glm;
        break;
      default:
        throw new IllegalStateException("Unsupported Layout Manager");
    }
    recyclerView.setLayoutManager(lm);
    //if we has been already attached no observer will be registered
  }

  @BindingAdapter("adapter")
  public static void _bindAdapter(final ViewPager viewPager,
      @Nullable final AbstractVmAdapter adapter) {
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
      @Nullable final AbstractVmAdapter adapter) {
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
