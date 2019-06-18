package com.skiff2011.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.view.BaseViewHolder;
import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractItemAdapter<V extends Item> extends BaseObservable
    implements ItemAdapter<V> {

  public int indexOf(@NonNull V vm) {
    return vms().indexOf(vm);
  }

  public int lastIndexOf(@NonNull V vm) {
    return vms().lastIndexOf(vm);
  }

  public boolean contains(@NonNull V vm) {
    return vms().contains(vm);
  }

  public boolean containsAll(@NonNull Collection<? extends V> c) {
    return vms().containsAll(c);
  }

  @NonNull public ListIterator<V> listIterator() {
    return listIterator(0);
  }

  public boolean remove(@NonNull V vm) {
    int index = indexOf(vm);
    if (index < 0) {
      return false;
    } else {
      remove(index);
      return true;
    }
  }

  public void clear() {
    clear(false);
  }

  public void add(@NonNull V vm) {
    add(vms().size(), vm);
  }

  public boolean addAll(@NonNull Collection<? extends V> c) {
    return addAll(vms().size(), c);
  }

  public boolean removeIf(@NonNull Predicate<V> predicate) {
    return removeIf(predicate, false);
  }

  public void set(@NonNull Collection<? extends V> c) {
    set(c, false);
  }

  public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    viewHolder.bindViewModel(get(position));
  }

  public @Bindable boolean isEmpty() {
    return getSize() == 0;
  }

  public void refresh() {

  }
}
