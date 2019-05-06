package com.skiff2011.baseadapter.additionals;

import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;

abstract public class AdditionalBindingAdapters {

  @BindingAdapter("adapter")
  public static void _bindAdapter(final AdapterView adapterView,
      @Nullable final AbstractVmAdapter adapter) {
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
  public static void _bindAdapter(final Spinner spinner, @Nullable final AbstractVmAdapter adapter,
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
      @Nullable final AbstractVmAdapter adapter) {
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
}
