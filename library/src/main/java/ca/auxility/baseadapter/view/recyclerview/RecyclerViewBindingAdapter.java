package ca.auxility.baseadapter.view.recyclerview;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import ca.auxility.baseadapter.Adapter;

public class RecyclerViewBindingAdapter {
  @BindingAdapter("adapter")
  public static void _bindAdapter(final RecyclerView recyclerView,
      @Nullable final Adapter adapter) {

    final RecyclerViewAdapter decorator = adapter == null ? null : new RecyclerViewAdapter(adapter);

    if (decorator != recyclerView.getAdapter()) {
      recyclerView.setAdapter(decorator);
    }
  }
}
