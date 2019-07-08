package dev.auxility.baseadapter.additionals;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import com.google.android.material.tabs.TabLayout;
import dev.auxility.baseadapter.AbstractAdapter;
import dev.auxility.baseadapter.AdapterDataObserver;
import dev.auxility.baseadapter.view.viewpager.ViewPagerAdapter;

abstract public class AdditionalBindingAdapters {

  @BindingAdapter("adapter")
  public static void _bindAdapter(final AdapterView adapterView,
      @Nullable final AbstractAdapter adapter) {
    //if adapter instance was changed while adapterView was attached to screen
    // we have to unsubscribe the previous adapter
    android.widget.Adapter prevAdapter = adapterView.getAdapter();
    if (adapter instanceof AdapterDataObserver
        && prevAdapter instanceof AdapterDataObserver) {
      adapter.unregisterObserver((AdapterDataObserver) prevAdapter);
    }
    if (adapter == null) {
      adapterView.setAdapter(null);
      return;
    }
    final ListAdapter decorator = new ListAdapter(adapter);
    adapterView.setAdapter(decorator);
    //if adapter instance was changed while adapterView was attached to screen
    //we have register observer since view is already attached to window
    if (adapterView.getWindowToken() != null) {
      adapter.registerObserver(decorator);
    }
    adapterView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
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

  @BindingAdapter(value = { "adapter", "hintEnabled" }, requireAll = false)
  public static void _bindAdapter(final Spinner spinner,
      @Nullable final AbstractAdapter adapter,
      boolean hintEnabled) {
    //if adapter instance was changed while spinner was attached to screen
    // we have to unsubscribe the previous adapter
    android.widget.Adapter prevAdapter = spinner.getAdapter();
    if (adapter instanceof AdapterDataObserver && prevAdapter instanceof AdapterDataObserver) {
      adapter.unregisterObserver((AdapterDataObserver) prevAdapter);
    }
    if (adapter == null) {
      spinner.setAdapter(null);
      return;
    }
    final SpinnerAdapter decorator = new SpinnerAdapter(adapter, hintEnabled);
    spinner.setAdapter(decorator);
    //if adapter instance was changed while spinner was attached to screen
    //we have register observer since view is already attached to window
    if (spinner.getWindowToken() != null) {
      adapter.registerObserver(decorator);
    }
    spinner.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
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
  public static void _bindAdapter(final AutoCompleteTextView autoCompleteTextView,
      @Nullable final AbstractAdapter adapter) {
    //if adapter instance was changed while autoCompleteTextView was attached to screen
    // we have to unsubscribe the previous adapter
    android.widget.Adapter prevAdapter = autoCompleteTextView.getAdapter();
    if (adapter instanceof AdapterDataObserver && prevAdapter instanceof AdapterDataObserver) {
      adapter.unregisterObserver((AdapterDataObserver) prevAdapter);
    }
    if (adapter == null) {
      autoCompleteTextView.setAdapter(null);
      return;
    }
    final FilterableListAdapter decorator = new FilterableListAdapter(adapter);
    autoCompleteTextView.setAdapter(decorator);
    //if adapter instance was changed while autoCompleteTextView was attached to screen
    //we have register observer since view is already attached to window
    if (autoCompleteTextView.getWindowToken() != null) {
      adapter.registerObserver(decorator);
    }
    autoCompleteTextView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
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
      @Nullable final AbstractAdapter adapter) {
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
