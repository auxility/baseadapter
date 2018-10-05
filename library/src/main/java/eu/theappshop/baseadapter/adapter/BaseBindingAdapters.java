package eu.theappshop.baseadapter.adapter;

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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import eu.theappshop.baseadapter.misc.LayoutManagerType;
import eu.theappshop.baseadapter.vm.SpannedVM;
import eu.theappshop.baseadapter.vm.VM;

public final class BaseBindingAdapters {

  @BindingAdapter(value = {
      "adapter", "layoutManager", "reverse", "spanCount", "orientation"
  }, requireAll = false)
  public static void _bindAdapter(final RecyclerView recyclerView,
      @Nullable final Adapter adapter,
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
            VM vm = adapter.getItem(position);
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
  public static void _bindAdapter(final ViewPager viewPager, @Nullable final Adapter adapter) {
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
  public static void _bindAdapter(final TabLayout tabLayout, @Nullable final Adapter adapter) {
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

  @BindingAdapter("adapter")
  public static void _bindAdapter(final AdapterView adapterView, @Nullable final Adapter adapter) {
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
  public static void _bindAdapter(final Spinner spinner, @Nullable final Adapter adapter,
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
      @Nullable final Adapter adapter) {
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
