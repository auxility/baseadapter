package dev.auxility.baseadapter.view.viewpager;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import dev.auxility.baseadapter.Adapter;
import dev.auxility.baseadapter.AdapterDataObserver;

public class ViewPagerBindingAdapters {

  @BindingAdapter("adapter")
  public static void _bindAdapter(final ViewPager viewPager,
      @Nullable final Adapter adapter) {
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
}
