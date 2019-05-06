package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;
import java.util.List;

public interface AdapterDataObserver<V extends VM> {

  void notifyOnDataSetChanged();

  void notifyOnItemInserted(int position);

  void notifyOnItemRangeInserted(int positionStart, int itemCount);

  void notifyOnItemRemoved(int position);

  void notifyOnItemRangeRemoved(int positionStart, int itemCount);

  void notifyOnDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms);

  void notifyOnItemChanged(int position);
}
