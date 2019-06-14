package com.skiff2011.baseadapter.view.recyclerview.layoutmanager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.skiff2011.baseadapter.ItemAdapter;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.item.SpanItem;
import com.skiff2011.baseadapter.view.recyclerview.RecyclerViewAdapter;

public class SpanGridLayoutManager extends GridLayoutManager {

  private static final int DEFAULT_MAX_SPAN_COUNT = 12;

  public SpanGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(null);
  }

  public SpanGridLayoutManager(Context context, int spanCount, ItemAdapter adapter) {
    super(context, spanCount);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, ItemAdapter adapter) {
    super(context, DEFAULT_MAX_SPAN_COUNT);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, int spanCount, int orientation,
      boolean reverseLayout, ItemAdapter adapter) {
    super(context, spanCount, orientation, reverseLayout);
    init(adapter);
  }

  public SpanGridLayoutManager(Context context, int orientation,
      boolean reverseLayout, ItemAdapter adapter) {
    super(context, DEFAULT_MAX_SPAN_COUNT, orientation, reverseLayout);
    init(adapter);
  }

  private void init(final ItemAdapter adapter) {
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
    ItemAdapter prevItemAdapter = null;
    ItemAdapter newItemAdapter = null;
    if (oldAdapter instanceof RecyclerViewAdapter) {
      prevItemAdapter = ((RecyclerViewAdapter) oldAdapter).getAdapter();
    }
    if (newAdapter instanceof RecyclerViewAdapter) {
      newItemAdapter = ((RecyclerViewAdapter) newAdapter).getAdapter();
    }
    if (newItemAdapter != prevItemAdapter) {
      init(newItemAdapter);
    }
  }
}