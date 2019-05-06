package com.skiff2011.baseadapter.view;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.misc.DiffVMCallback;
import com.skiff2011.baseadapter.vm.VM;
import java.util.List;

public class RecyclerViewAdapter<V extends VM> extends RecyclerView.Adapter<RecyclerBindingHolder>
    implements AdapterDataObserver<V> {

  @NonNull
  private AbstractVmAdapter<V> adapter;

  RecyclerViewAdapter(@NonNull AbstractVmAdapter<V> adapter) {
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
    adapter.bindViewHolder(holder, position);
  }

  @Override
  public int getItemCount() {
    return adapter.getSize();
  }

  @Override
  public int getItemViewType(int position) {
    return adapter.get(position).getLayoutId();
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    adapter.registerObserver(this);
  }

  @Override
  public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    adapter.unregisterObserver(this);
  }

  @NonNull public AbstractVmAdapter getAdapter() {
    return adapter;
  }

  @Override public void notifyOnItemInserted(int position) {
    notifyItemInserted(position);
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int itemCount) {
    notifyItemRangeInserted(positionStart, itemCount);
  }

  @Override public void notifyOnItemRemoved(int position) {
    notifyItemRemoved(position);
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    notifyItemRangeRemoved(positionStart, itemCount);
  }

  @Override public void notifyOnItemChanged(int position) {
    notifyItemChanged(position);
  }

  @Override public void notifyOnDataSetChanged() {
    notifyDataSetChanged();
  }

  @Override
  public void notifyOnDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    // TODO: https://medium.com/@jonfhancock/get-threading-right-with-diffutil-423378e126d2
    DiffUtil.DiffResult diffResult =
        DiffUtil.calculateDiff(new DiffVMCallback<>(oldItems, newVms));
    diffResult.dispatchUpdatesTo(this);
  }
}
