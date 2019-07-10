package dev.auxility.baseadapter.view.recyclerview.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.auxility.baseadapter.Adapter;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.item.SpanItem;
import dev.auxility.baseadapter.view.recyclerview.RecyclerViewAdapter;

public class SpanGridLayoutManager extends GridLayoutManager {

  private static final int DEFAULT_MAX_SPAN_COUNT = 12;

  public SpanGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(null);
  }

  public SpanGridLayoutManager(Context context, int spanCount, Adapter adapter) {
    super(context, spanCount);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, Adapter adapter) {
    super(context, DEFAULT_MAX_SPAN_COUNT);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, int spanCount, int orientation,
      boolean reverseLayout, Adapter adapter) {
    super(context, spanCount, orientation, reverseLayout);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, int orientation,
      boolean reverseLayout, Adapter adapter) {
    super(context, DEFAULT_MAX_SPAN_COUNT, orientation, reverseLayout);
    init(adapter);
  }

  private void init(final Adapter adapter) {
    setSpanSizeLookup(new SpanSizeLookup() {
      @Override public int getSpanSize(int i) {
        if (adapter == null) {
          return 1;
        } else {
          Item item = adapter.get(i);
          if (item instanceof SpanItem) {
            SpanItem sItem = (SpanItem) item;
            return sItem.getSpanSize();
          } else {
            return 1;
          }
        }
      }
    });
  }

  @Override public void onAdapterChanged(@Nullable RecyclerView.Adapter oldAdapter,
      @Nullable RecyclerView.Adapter newAdapter) {
    //TODO test equal adapters change
    super.onAdapterChanged(oldAdapter, newAdapter);
    Adapter prevAdapter = null;
    Adapter newItemAdapter = null;
    if (oldAdapter instanceof RecyclerViewAdapter) {
      prevAdapter = ((RecyclerViewAdapter) oldAdapter).getAdapter();
    }
    if (newAdapter instanceof RecyclerViewAdapter) {
      newItemAdapter = ((RecyclerViewAdapter) newAdapter).getAdapter();
    }
    if (newItemAdapter != prevAdapter) {
      init(newItemAdapter);
    }
  }
}
