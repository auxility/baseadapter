package eu.theappshop.baseadapter.misc;

import android.support.v7.util.DiffUtil;
import eu.theappshop.baseadapter.vm.DiffVM;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

public class DiffVMCallback<V extends VM> extends DiffUtil.Callback {

  private List<V> oldList;
  private List<V> newList;

  public DiffVMCallback(List<V> oldList, List<V> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    VM oldItem = oldList.get(oldItemPosition);
    VM newItem = newList.get(newItemPosition);
    return oldItem instanceof DiffVM && newItem instanceof DiffVM && ((DiffVM) newItem).isEqualItem(
        oldItem);
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    VM oldItem = oldList.get(oldItemPosition);
    VM newItem = newList.get(newItemPosition);
    return oldItem instanceof DiffVM
        && newItem instanceof DiffVM
        && ((DiffVM) newItem).isEqualContent(oldItem);
  }
}
