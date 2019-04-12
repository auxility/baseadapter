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

  }

  @Override public void onNotifyItemInserted(int position) {

  }

  @Override public void onNotifyItemRangeInserted(int positionStart, int itemCount) {

  }

  @Override public void onNotifyItemRemoved(int position) {

  }

  @Override public void onNotifyItemRangeRemoved(int positionStart, int itemCount) {

  }

  @Override public void onNotifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {

  }

  @Override public void onNotifyItemChanged(int position) {

  }

  private void updateDataFromAdapter() {
    vms = new ArrayList<>();
    for (int i = 0; i < adapter.getSize(); i++) {
      vms.add(adapter.get(i));
    }
  }
}
