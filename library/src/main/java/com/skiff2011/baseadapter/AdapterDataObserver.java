package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.item.Item;
import java.util.List;

public interface AdapterDataObserver {

  void notifyOnDataSetChanged();

  void notifyOnItemInserted(int position);

  void notifyOnItemRangeInserted(int positionStart, int itemCount);

  void notifyOnItemRemoved(int position);

  void notifyOnItemRangeRemoved(int positionStart, int itemCount);

  void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms);

  void notifyOnItemChanged(int position);
}
