package eu.theappshop.baseadapter.adapter;

import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

interface AdapterDataObserver {

  void notifyDataSetChanged();

  void notifyItemInserted(int position);

  void notifyItemRangeInserted(int positionStart, int itemCount);

  void notifyItemRemoved(int position);

  void notifyItemRangeRemoved(int positionStart, int itemCount);

  void refresh(List<VM> oldItems);
}
