package ca.auxility.baseadapter.view.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ca.auxility.baseadapter.Adapter;
import ca.auxility.baseadapter.AdapterDataObserver;
import ca.auxility.baseadapter.item.Item;
import ca.auxility.baseadapter.misc.DiffItemCallback;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerBindingHolder>
    implements AdapterDataObserver {

  @NonNull
  private Adapter adapter;

  RecyclerViewAdapter(@NonNull Adapter adapter) {
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

  @NonNull public Adapter getAdapter() {
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
  public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms) {
    // TODO: https://medium.com/@jonfhancock/get-threading-right-with-diffutil-423378e126d2
    DiffUtil.DiffResult diffResult =
        DiffUtil.calculateDiff(new DiffItemCallback(oldItems, newVms));
    diffResult.dispatchUpdatesTo(this);
  }
}
