package ca.auxility.baseadapter.misc;

import androidx.recyclerview.widget.DiffUtil;
import ca.auxility.baseadapter.item.DiffItem;
import ca.auxility.baseadapter.item.Item;
import java.util.List;

public class DiffItemCallback extends DiffUtil.Callback {

  private List<? extends Item> oldList;
  private List<? extends Item> newList;

  public DiffItemCallback(List<? extends Item> oldList, List<? extends Item> newList) {
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
    Item oldItem = oldList.get(oldItemPosition);
    Item newItem = newList.get(newItemPosition);
    if (newItem instanceof DiffItem) {
      return ((DiffItem) newItem).isEqualItem(oldItem);
    } else {
      return oldItem.equals(newItem);
    }
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    Item oldItem = oldList.get(oldItemPosition);
    Item newItem = newList.get(newItemPosition);
    if (newItem instanceof DiffItem) {
      return ((DiffItem) newItem).isEqualContent(oldItem);
    } else {
      return oldItem.equals(newItem);
    }
  }
}
