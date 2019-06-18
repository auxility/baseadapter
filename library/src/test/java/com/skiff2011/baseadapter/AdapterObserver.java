package com.skiff2011.baseadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.item.Item;
import java.util.ArrayList;
import java.util.List;

public class AdapterObserver<V extends Item> implements AdapterDataObserver {

  public List<V> vms;
  private AbstractItemAdapter<V> adapter;

  public AdapterObserver(AbstractItemAdapter<V> adapter) {
    this.adapter = adapter;
    updateDataFromAdapter();
  }

  @Override public void notifyOnDataSetChanged() {
    updateDataFromAdapter();
  }

  @Override public void notifyOnItemInserted(int position) {
    this.vms.add(position, this.adapter.get(position));
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int positionEnd) {
    this.vms.addAll(positionStart, getRange(positionStart, positionEnd));
  }

  @Override public void notifyOnItemRemoved(int position) {
    this.vms.remove(position);
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    this.vms.subList(positionStart, itemCount).clear();
  }

  @Override public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms) {
    updateDataFromAdapter();
  }

  @Override public void notifyOnItemChanged(int position) {
    this.vms.set(position, this.adapter.get(position));
  }

  private void updateDataFromAdapter() {
    this.vms = new ArrayList<>();
    for (int i = 0; i < this.adapter.getSize(); i++) {
      this.vms.add(this.adapter.get(i));
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
