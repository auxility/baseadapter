package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

public interface AdapterDataObserver<V extends VM> {

  void notifyDataSetChanged();

  void notifyItemInserted(int position);

  void notifyItemRangeInserted(int positionStart, int itemCount);

  void notifyItemRemoved(int position);

  void notifyItemRangeRemoved(int positionStart, int itemCount);

  void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms);

  void notifyItemChanged(int position);
}
