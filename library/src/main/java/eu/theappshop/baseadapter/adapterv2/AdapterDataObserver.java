package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

interface AdapterDataObserver<V extends VM> {

  void onNotifyDataSetChanged();

  void onNotifyItemInserted(int position);

  void onNotifyItemRangeInserted(int positionStart, int itemCount);

  void onNotifyItemRemoved(int position);

  void onNotifyItemRangeRemoved(int positionStart, int itemCount);

  void onNotifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms);

  void onNotifyItemChanged(int position);
}
