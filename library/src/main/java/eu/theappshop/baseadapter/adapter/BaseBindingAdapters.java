package eu.theappshop.baseadapter.adapter;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import eu.theappshop.baseadapter.misc.LayoutManagerType;
import eu.theappshop.baseadapter.vm.SpannedVM;
import eu.theappshop.baseadapter.vm.VM;

public final class BaseBindingAdapters {

  @BindingAdapter(value = {
      "adapter", "layoutManager", "reverse", "spanCount", "orientation"
  }, requireAll = false)
  public static void _bindAdapter(final RecyclerView recyclerView, final Adapter adapter,
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
    recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
      @Override public void onViewAttachedToWindow(View v) {
        if (decorator != null) {
          adapter.registerObserver(decorator);
        }
      }

      @Override public void onViewDetachedFromWindow(View v) {
        if (decorator != null) {
          adapter.unregisterObserver(decorator);
        }
      }
    });
  }

  @BindingAdapter("adapter")
  public static void _bindAdapter(final ViewPager viewPager, final Adapter adapter) {
    final ViewPagerAdapter decorator = new ViewPagerAdapter(adapter);
    viewPager.setAdapter(decorator);

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
