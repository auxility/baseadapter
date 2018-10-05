package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import eu.theappshop.baseadapter.misc.DiffVMCallback;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

public class RecyclerViewAdapter<V extends VM> extends RecyclerView.Adapter<RecyclerBindingHolder>
    implements AdapterDataObserver<V> {

  @NonNull
  private Adapter<V> adapter;

  RecyclerViewAdapter(@NonNull Adapter<V> adapter) {
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
    return adapter.getItemCount();
  }

  @Override
  public int getItemViewType(int position) {
    return adapter.getItemViewType(position);
  }

  @Override
  public void notifyDataSetChanged(List<V> oldItems, List<V> newVms) {
    // TODO: https://medium.com/@jonfhancock/get-threading-right-with-diffutil-423378e126d2
    DiffUtil.DiffResult diffResult =
            DiffUtil.calculateDiff(new DiffVMCallback<>(oldItems, adapter.getItems()));
    diffResult.dispatchUpdatesTo(this);
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

  @NonNull public Adapter getAdapter() {
    return adapter;
  }
}
