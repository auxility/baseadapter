package eu.theappshop.baseadapter.adapterv2;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;
import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractVmAdapter<V extends VM> extends BaseObservable
    implements VmAdapter<V> {

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
