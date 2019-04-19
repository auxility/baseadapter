package eu.theappshop.baseadapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;
import eu.theappshop.baseadapter.adapterv2.AdapterDataObserver;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class AdapterObserver<V extends VM> implements AdapterDataObserver<V> {

  public List<V> vms;
  private AbstractVmAdapter<V> adapter;

  public AdapterObserver(AbstractVmAdapter<V> adapter) {
    this.adapter = adapter;
    updateDataFromAdapter();
  }

  @Override public void notifyDataSetChanged() {
    updateDataFromAdapter();
  }

  @Override public void notifyItemInserted(int position) {
    this.vms.add(position, this.adapter.get(position));
  }

  @Override public void notifyItemRangeInserted(int positionStart, int positionEnd) {
    this.vms.addAll(positionStart, getRange(positionStart, positionEnd));
  }

  @Override public void notifyItemRemoved(int position) {
    this.vms.remove(position);
  }

  @Override public void notifyItemRangeRemoved(int positionStart, int itemCount) {
    this.vms.subList(positionStart, itemCount).clear();
  }

  @Override public void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    updateDataFromAdapter();
  }

  @Override public void notifyItemChanged(int position) {
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
