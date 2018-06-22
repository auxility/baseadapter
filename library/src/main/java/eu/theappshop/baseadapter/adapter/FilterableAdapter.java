package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
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

  @Override
  public void add(V item) {
    allVms.add(item);
    refresh();
  }

  @Override
  public void add(int position, V item) {
    allVms.add(position, item);
    refresh();
  }

  @Override
  public void addAll(List<? extends V> list) {
    allVms.addAll(list);
    refresh();
  }

  @Override
  public void addAll(List<? extends V> list, int position) {
    allVms.addAll(position, list);
    refresh();
  }

  @Override
  public void remove(V v) {
    int index = allVms.indexOf(v);
    if (index != -1) {
      allVms.remove(index);
      refresh();
    }
  }

  @Override
  public V remove(int index) {
    if (index != -1) {
      V v = allVms.remove(index);
      refresh();
      return v;
    }
    return null;
  }

  @Override
  public void removeRange(int start, int end) {
    allVms.subList(start, end).clear();
    refresh();
  }

  public int getUnfilteredItemCount() {
    return allVms.size();
  }

  public List<V> getUnfilteredItems() {
    return allVms;
  }

  public V getUnfilteredtItem(int position) {
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
      if (getUnfilteredtItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }

  public int findLastIndexOfUnfiltered(Class<? extends V> cls) {
    for (int i = getUnfilteredItemCount() - 1; i >= 0; i--) {
      if (getUnfilteredtItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }
}
