package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FilterableAdapter<V extends VM> extends BaseAdapter<V> {

  private final List<Filter<V>> filters;

  private List<V> allVms;

  public FilterableAdapter() {
    this(new ArrayList<V>());
  }

  public FilterableAdapter(List<V> allVms) {
    this(allVms, new LinkedList<Filter<V>>());
  }

  public FilterableAdapter(List<V> allVms, List<Filter<V>> filters) {
    this.allVms = allVms;
    this.filters = filters;
    refresh();
  }

  @Override
  public void refresh() {
    List<VM> oldVMs = (List<VM>) getItems();
    setVMs(filter());
    for (AdapterDataObserver observer : mObservers) {
      observer.refresh(oldVMs);
    }
  }

  private List<V> filter() {
    List<V> newVMs = allVms;
    for (Filter<V> filter : filters) {
      newVMs = filter.filter(newVMs);
    }
    return newVMs;
  }

  @Override
  public void clear() {
    allVms.clear();
    refresh();
  }

  public void addFilter(Filter<V> filter) {
    filters.add(filter);
    refresh();
  }

  public void addFilters(Collection<Filter<V>> filters) {
    this.filters.addAll(filters);
    refresh();
  }

  public void clearFilters() {
    filters.clear();
    refresh();
  }

  public void removeFilter(int position) {
    filters.remove(position);
    refresh();
  }

  public List<Filter<V>> getFilters() {
    return filters;
  }

  @Override
  public void add(V item) {
    super.add(item);
    allVms.add(item);
  }

  @Override
  public void add(int position, V item) {
    super.add(position, item);
    V prevItem = getItem(position - 1);
    int allVmsIndex = allVms.indexOf(prevItem) + 1;
    allVms.add(allVmsIndex, item);
  }

  @Override
  public void addAll(List<? extends V> list) {
    super.addAll(list);
    allVms.addAll(list);
  }

  @Override
  public void addAll(List<? extends V> list, int position) {
    super.addAll(list, position);
    V prevItem = getItem(position - 1);
    int allVmsIndex = allVms.indexOf(prevItem) + 1;
    allVms.addAll(allVmsIndex, list);
  }

  @Override
  public void remove(V v) {
    super.remove(v);
    allVms.remove(v);
  }

  @Override
  public V remove(int index) {
    V vm = super.remove(index);
    allVms.remove(vm);
    return vm;
  }

  @Override
  public List<V> removeRange(int start, int end) {
    List<V> removedVms = super.removeRange(start, end);
    allVms.removeAll(removedVms);
    return removedVms;
  }

  public int getUnfilteredItemCount() {
    return allVms.size();
  }

  public List<V> getUnfilteredItems() {
    return allVms;
  }

  public V getUnfilteredItem(int position) {
    return allVms.get(position);
  }

  public int indexOfUnfiltered(V o) {
    return allVms.indexOf(o);
  }

  public int lastIndexOfUnfiltered(V o) {
    return allVms.lastIndexOf(o);
  }

  @NonNull
  public Iterator<V> unfilteredIterator() {
    return allVms.iterator();
  }

  public int findFirstIndexOfUnfiltered(Class<? extends V> cls) {
    for (int i = 0; i < getUnfilteredItemCount(); i++) {
      if (getUnfilteredItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }

  public int findLastIndexOfUnfiltered(Class<? extends V> cls) {
    for (int i = getUnfilteredItemCount() - 1; i >= 0; i--) {
      if (getUnfilteredItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }
}
