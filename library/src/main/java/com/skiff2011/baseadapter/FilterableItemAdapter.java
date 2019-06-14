package com.skiff2011.baseadapter;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.misc.ListUtils;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.misc.function.SerializablePredicate;
import com.skiff2011.baseadapter.view.BaseViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterableItemAdapter<V extends Item> extends AbstractItemAdapterDecorator<V> {

  @NonNull private SerializablePredicate<V> filter;
  @NonNull private List<V> vms;

  public FilterableItemAdapter(
      @NonNull AbstractItemAdapter<V> adapter,
      @NonNull List<V> vms,
      @NonNull SerializablePredicate<V> filter) {
    super(adapter);
    this.vms = vms;
    this.filter = filter;
    refresh();
  }

  public FilterableItemAdapter(
      @NonNull List<V> vms,
      @NonNull SerializablePredicate<V> filter) {
    this(new BaseItemAdapter<V>(), vms, filter);
  }

  public FilterableItemAdapter(
      @NonNull AbstractItemAdapter<V> adapter, @NonNull SerializablePredicate<V> filter) {
    this(adapter, new ArrayList<V>(), filter);
  }

  public FilterableItemAdapter(
      @NonNull SerializablePredicate<V> filter) {
    this(new BaseItemAdapter<V>(), filter);
  }

  public FilterableItemAdapter() {
    this(new SerializablePredicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return true;
      }
    });
  }

  @NonNull public SerializablePredicate<V> getFilter() {
    return filter;
  }

  public void setFilter(@NonNull SerializablePredicate<V> filter) {
    this.filter = filter;
    refresh();
  }

  @Bindable @Override public int getSize() {
    return getAdapter().getSize();
  }

  @Bindable @Override public boolean isEmpty() {
    return getAdapter().isEmpty();
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.vms.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public V remove(int index) {
    V vm = vms.remove(index);
    if (filter.apply(vm)) {
      getAdapter().remove(vm);
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
    getAdapter().set(ListUtils.filter(vms, filter), withDiffUtil);
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
        getAdapter().remove(firstVm);
      } else {
        getAdapter()
            .removeRange(getAdapter().indexOf(firstVm),
                getAdapter().indexOf(lastVm) + 1);
      }
    }
    return itemsToReturn;
  }

  //TODO remove and use remove range
  @Override public void clear(boolean withDiffUtil) {
    this.vms.clear();
    getAdapter().clear(withDiffUtil);
  }

  @Override public void add(int index, @NonNull V element) {
    this.vms.add(index, element);
    if (filter.apply(element)) {
      if (index == this.vms.size() - 1) {
        getAdapter().add(element);
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
      getAdapter().addAll(newFilteredVms);
    } else {
      refresh();
    }
    return returnValue;
  }

  @NonNull @Override public V get(int index) {
    return getAdapter().get(index);
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
    getAdapter().set(filteredVms, withDiffUtil);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return new AdapterListIterator<>(vms.listIterator(), new AdapterListIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(vms.listIterator(index), new AdapterListIteratorListenerImpl(),
        index);
  }

  @Override public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    getAdapter().bindViewHolder(viewHolder, position);
  }

  @Override public void refresh() {
    getAdapter().set(ListUtils.filter(this.vms, filter));
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof FilterableItemAdapter)) {
      return false;
    }
    FilterableItemAdapter other = (FilterableItemAdapter) obj;
    return this.vms.equals(other.vms) && getAdapter().equals(
        other.getAdapter());
  }

  @Override public int hashCode() {
    return this.vms.hashCode() * 1000 + getAdapter().hashCode();
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener<V> {

    @Override public void onItemRemoved(int position, V item) {
      if (filter.apply(item)) {
        getAdapter().remove(item);
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
        getAdapter().set(getAdapter().indexOf(oldItem), item);
      } else if (itemFiltered) {
        refresh();
      } else if (prevFiltered) {
        getAdapter().remove(oldItem);
      }
    }
  }
}
