package com.skiff2011.baseadapter.view.recyclerview;

import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import com.skiff2011.baseadapter.ItemAdapter;

public class RecyclerViewBindingAdapter {
  @BindingAdapter("adapter")
  public static void _bindAdapter(final RecyclerView recyclerView,
      @Nullable final ItemAdapter adapter) {

    final RecyclerViewAdapter decorator = adapter == null ? null : new RecyclerViewAdapter(adapter);

    if (decorator != recyclerView.getAdapter()) {
      recyclerView.setAdapter(decorator);
    }
  }
}
