package eu.theappshop.baseadapter.adapterv2;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterableVmAdapter<V extends VM> extends AbstractVmAdapter<V> implements Adapter<V> {

  @NonNull private Predicate<V> filter;
  @NonNull private final Adapter<V> adapter;
  @NonNull private List<V> vms;

  public FilterableVmAdapter(
      @NonNull Predicate<V> filter,
      @NonNull Adapter<V> adapter,
      @NonNull List<V> vms) {
    this.vms = vms;
    this.filter = filter;
    this.adapter = adapter;
    refresh();
  }

  public FilterableVmAdapter(
      @NonNull Predicate<V> filter,
      @NonNull List<V> vms) {
    this(filter, new VmAdapter<V>(), vms);
    refresh();
  }

  public FilterableVmAdapter(
      @NonNull Predicate<V> filter,
      @NonNull Adapter<V> adapter) {
    this(filter, adapter, new ArrayList<V>());
  }

  public FilterableVmAdapter(
      @NonNull Predicate<V> filter) {
    this(filter, new VmAdapter<V>());
  }

  public FilterableVmAdapter() {
    this(new Predicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return true;
      }
    });
  }

  @NonNull public Predicate<V> getFilter() {
    return filter;
  }

  public void setFilter(@NonNull Predicate<V> filter) {
    this.filter = filter;
    refresh();
  }

  @Bindable @Override public int getSize() {
    return this.adapter.getSize();
  }

  @Bindable @Override public boolean isEmpty() {
    return this.adapter.isEmpty();
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.vms.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public V remove(int index) {
    V vm = vms.remove(index);
    if (filter.apply(vm)) {
      this.adapter.remove(vm);
    }
    return vm;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    Iterator<V> iterator = vms.iterator();
    int prevSize = vms.size();
    while (iterator.hasNext()) {
      V vm = iterator.next();
      if (predicate.apply(vm)) {
        iterator.remove();
      }
    }
    if (vms.size() == prevSize) {
      return false;
    }
    this.adapter.set(ListUtils.filter(vms, filter), withDiffUtil);
    return true;
  }

  //TODO test with beginIndex == endIndex
  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    List<V> itemsToRemove = vms.subList(beginIndex, endIndex);
    List<V> itemsToReturn = new ArrayList<>(itemsToRemove);
    itemsToRemove.clear();
    V firstVm = ListUtils.first(itemsToReturn, new Predicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return null;
      }
    });
    V lastVm = ListUtils.first(itemsToReturn, new Predicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return null;
      }
    });
    if (firstVm != null && lastVm != null) {
      if (firstVm == lastVm) {
        this.adapter.remove(firstVm);
      } else {
        this.adapter.removeRange(adapter.indexOf(firstVm), adapter.indexOf(lastVm) + 1);
      }
    }
    return itemsToReturn;
  }

  //TODO remove and use remove range
  @Override public void clear(boolean withDiffUtil) {
    this.vms.clear();
    this.adapter.clear(withDiffUtil);
  }

  @Override public void add(int index, @NonNull V element) {
    this.vms.add(index, element);
    if (filter.apply(element)) {
      if (index == this.vms.size() - 1) {
        this.adapter.add(element);
      } else {
        refresh();
      }
    }
  }

  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    List<V> newVms = new ArrayList<>(c);
    boolean returnValue = this.vms.addAll(index, newVms);
    if (index == this.vms.size()) {
      List<V> newFilteredVms = ListUtils.filter(newVms, filter);
      this.adapter.addAll(newFilteredVms);
    } else {
      refresh();
    }
    return returnValue;
  }

  @NonNull @Override public V get(int index) {
    return this.vms.get(index);
  }

  @NonNull @Override public List<V> vms() {
    return Collections.unmodifiableList(this.vms);
  }

  @NonNull @Override public V set(int index, @NonNull V element) {
    V prevVm = this.vms.set(index, element);
    if (this.filter.apply(element)) {
      refresh();
    }
    return prevVm;
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    List<V> newVms = new ArrayList<>(c);
    List<V> filteredVms = ListUtils.filter(newVms, filter);
    this.vms = newVms;
    this.adapter.set(filteredVms, withDiffUtil);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return new AdapterListIterator<>(vms.listIterator(), new AdapterListIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(vms.listIterator(index), new AdapterListIteratorListenerImpl(),
        index);
  }

  @Override public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    this.adapter.bindViewHolder(viewHolder, position);
  }

  @Override public void refresh() {
    this.adapter.set(ListUtils.filter(this.vms, filter));
  }

  @Override public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    this.adapter.addOnPropertyChangedCallback(callback);
  }

  @Override public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    this.adapter.removeOnPropertyChangedCallback(callback);
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    this.adapter.registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    this.adapter.unregisterObserver(observer);
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener<V> {

    @Override public void onItemRemoved(int position, V item) {
      if (filter.apply(item)) {
        adapter.remove(item);
      }
    }
  }

  private class AdapterListIteratorListenerImpl extends AdapterIteratorListenerImpl implements
      AdapterListIterator.AdapterListIteratorListener<V> {

    @Override public void onItemAdded(int position, V item) {
      if (filter.apply(item)) {
        refresh();
      }
    }

    @Override public void onItemChanged(int position, V item, V oldItem) {
      boolean itemFiltered = filter.apply(item);
      boolean prevFiltered = filter.apply(oldItem);
      if (itemFiltered && prevFiltered) {
        adapter.set(adapter.indexOf(oldItem), item);
      } else if (itemFiltered) {
        refresh();
      } else if (prevFiltered) {
        adapter.remove(oldItem);
      }
    }
  }
}
