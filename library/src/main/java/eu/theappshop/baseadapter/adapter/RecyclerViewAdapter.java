package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import eu.theappshop.baseadapter.misc.DiffVMCallback;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerBindingHolder>
    implements AdapterDataObserver {

  private Adapter<VM> abstractAdapter;

  RecyclerViewAdapter(BaseAdapter abstractAdapter) {
    this.abstractAdapter = abstractAdapter;
  }

  @NonNull
  @Override
  public RecyclerBindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return RecyclerBindingHolder.create(LayoutInflater.from(parent.getContext()), viewType, parent);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindViewHolder(@NonNull RecyclerBindingHolder holder, int position) {
    abstractAdapter.bindViewHolder(holder, position);
  }

  @Override
  public int getItemCount() {
    return abstractAdapter.getItemCount();
  }

  @Override
  public int getItemViewType(int position) {
    return abstractAdapter.getItemViewType(position);
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    abstractAdapter.registerObserver(this);
  }

  @Override
  public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    abstractAdapter.unregisterObserver(this);
  }

  @Override
  public void refresh(List<VM> oldItems) {
    DiffUtil.DiffResult diffResult =
        DiffUtil.calculateDiff(new DiffVMCallback(oldItems, abstractAdapter.getItems()));
    diffResult.dispatchUpdatesTo(this);
  }
}
