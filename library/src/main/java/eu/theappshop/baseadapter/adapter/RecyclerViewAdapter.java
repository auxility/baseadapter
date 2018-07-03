package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eu.theappshop.baseadapter.misc.DiffVMCallback;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerBindingHolder>
    implements AdapterDataObserver, RecyclerView.OnAttachStateChangeListener {

  @Nullable
  private Adapter<VM> adapter;

  RecyclerViewAdapter(@Nullable Adapter adapter) {
    this.adapter = adapter;
  }

  @NonNull
  @Override
  public RecyclerBindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return RecyclerBindingHolder.create(LayoutInflater.from(parent.getContext()), viewType, parent);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindViewHolder(@NonNull RecyclerBindingHolder holder, int position) {
    assert adapter != null;
    adapter.bindViewHolder(holder, position);
  }

  @Override
  public int getItemCount() {
    return adapter == null ? 0 : adapter.getItemCount();
  }

  @Override
  public int getItemViewType(int position) {
    assert adapter != null;
    return adapter.getItemViewType(position);
  }

  @Override
  public void refresh(List<VM> oldItems) {
    if (adapter != null) {
      DiffUtil.DiffResult diffResult =
          DiffUtil.calculateDiff(new DiffVMCallback(oldItems, adapter.getItems()));
      diffResult.dispatchUpdatesTo(this);
    }
  }

  @Override public void onViewAttachedToWindow(View v) {
    if (adapter != null) {
      adapter.registerObserver(this);
    }
  }

  @Override public void onViewDetachedFromWindow(View v) {
    if (adapter != null) {
      adapter.unregisterObserver(this);
    }
  }
}
