package eu.theappshop.baseadapter.adapterv2;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractVmAdapter<V extends VM> extends BaseObservable implements Adapter<V> {

  @Override public int indexOf(@NonNull V vm) {
    return vms().indexOf(vm);
  }

  @Override public int lastIndexOf(@NonNull V vm) {
    return vms().lastIndexOf(vm);
  }

  @Override public boolean contains(@NonNull V vm) {
    return vms().contains(vm);
  }

  @Override public boolean containsAll(@NonNull Collection<? extends V> c) {
    return vms().containsAll(c);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return listIterator(0);
  }

  @Override public boolean remove(@NonNull V vm) {
    int index = indexOf(vm);
    if (index < 0) {
      return false;
    } else {
      remove(index);
      return true;
    }
  }

  @Override public void clear() {
    clear(false);
  }

  @Override public void add(@NonNull V vm) {
    add(vms().size(), vm);
  }

  @Override public boolean addAll(@NonNull Collection<? extends V> c) {
    return addAll(vms().size(), c);
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate) {
    return removeIf(predicate, false);
  }

  @Override public void set(@NonNull Collection<? extends V> c) {
    set(c, false);
  }
}
