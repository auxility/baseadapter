package eu.theappshop.baseadapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapterv2.Adapter;
import eu.theappshop.baseadapter.adapterv2.AdapterDataObserver;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class AdapterObserver<V extends VM> implements AdapterDataObserver<V> {

  public List<V> vms;
  private Adapter<V> adapter;

  public AdapterObserver(Adapter<V> adapter) {
    this.adapter = adapter;
    updateDataFromAdapter();
  }

  @Override public void onNotifyDataSetChanged() {
    updateDataFromAdapter();
  }

  @Override public void onNotifyItemInserted(int position) {
    this.vms.add(position, this.adapter.get(position));
  }

  @Override public void onNotifyItemRangeInserted(int positionStart, int positionEnd) {
    this.vms.addAll(positionStart, getRange(positionStart, positionEnd));
  }

  @Override public void onNotifyItemRemoved(int position) {
    this.vms.remove(position);
  }

  @Override public void onNotifyItemRangeRemoved(int positionStart, int itemCount) {
    this.vms.subList(positionStart, itemCount).clear();
  }

  @Override public void onNotifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    updateDataFromAdapter();
  }

  @Override public void onNotifyItemChanged(int position) {
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
