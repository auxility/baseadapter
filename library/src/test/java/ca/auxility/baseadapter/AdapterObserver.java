package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.item.Item;
import java.util.ArrayList;
import java.util.List;

public class AdapterObserver<V extends Item> implements AdapterDataObserver {

  public List<V> items;
  private ItemAdapter<V> adapter;

  public AdapterObserver(ItemAdapter<V> adapter) {
    this.adapter = adapter;
    adapter.registerObserver(this);
    updateDataFromAdapter();
  }

  @Override public void notifyOnDataSetChanged() {
    updateDataFromAdapter();
  }

  @Override public void notifyOnItemInserted(int position) {
    this.items.add(position, this.adapter.get(position));
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int positionEnd) {
    this.items.addAll(positionStart, getRange(positionStart, positionEnd));
  }

  @Override public void notifyOnItemRemoved(int position) {
    this.items.remove(position);
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    this.items.subList(positionStart, itemCount).clear();
  }

  @Override public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms) {
    updateDataFromAdapter();
  }

  @Override public void notifyOnItemChanged(int position) {
    this.items.set(position, this.adapter.get(position));
  }

  private void updateDataFromAdapter() {
    this.items = new ArrayList<>();
    for (int i = 0; i < this.adapter.getSize(); i++) {
      this.items.add(this.adapter.get(i));
    }
  }

  private List<V> getRange(int positionStart, int positionEnd) {
    List<V> vmsToReturn = new ArrayList<>();
    for (int i = positionStart; i < positionEnd; i++) {
      vmsToReturn.add(this.adapter.get(i));
    }
    return vmsToReturn;
  }
}
