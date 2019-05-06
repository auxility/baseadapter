package com.skiff2011.baseadapter;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BaseVmAdapter<V extends VM> extends AbstractVmAdapter<V> {

  @NonNull private final ObservableAdapterImpl<V> observableAdapterDelegate =
      new ObservableAdapterImpl<>();

  @NonNull private List<V> vms;

  private int size;

  private boolean isEmpty;

  public BaseVmAdapter(@NonNull List<V> vms) {
    this.vms = vms;
    updateSize();
  }

  public BaseVmAdapter() {
    this(new ArrayList<V>());
  }

  @Bindable @Override public int getSize() {
    return this.size;
  }

  @Bindable @Override public boolean isEmpty() {
    return isEmpty;
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.vms.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(vms.listIterator(index), new AdapterListIteratorListenerImpl(),
        index);
  }

  @NonNull @Override public V remove(int index) {
    V e = vms.remove(index);
    updateSize();
    notifyItemRemoved(index);
    return e;
  }

  @Override public void clear(boolean withDiffUtil) {
    List<V> oldVms = vms();
    this.vms.clear();
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(oldVms, vms);
    } else {
      notifyDataSetChanged();
    }
  }

  @Override public void add(int index, @NonNull V element) {
    vms.add(index, element);
    updateSize();
    notifyItemInserted(index);
  }

  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    boolean returnValue = vms.addAll(index, c);
    updateSize();
    notifyItemRangeInserted(index, c.size());
    return returnValue;
  }

  @NonNull @Override public V get(int index) {
    return vms.get(index);
  }

  //to avoid vms list modification
  @NonNull @Override public List<V> vms() {
    return Collections.unmodifiableList(vms);
  }

  @NonNull @Override public V set(int index, @NonNull V element) {
    V returnValue = vms.set(index, element);
    notifyItemChanged(index);
    return returnValue;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    Iterator<V> iterator = vms.iterator();
    List<V> oldvms = new ArrayList<>(vms);
    while (iterator.hasNext()) {
      V vm = iterator.next();
      if (predicate.apply(vm)) {
        iterator.remove();
      }
    }
    if (oldvms.size() == vms.size()) {
      return false;
    }
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(oldvms, vms);
    } else {
      notifyDataSetChanged();
    }
    return true;
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    List<V> itemsToRemove = vms.subList(beginIndex, endIndex);
    List<V> itemsToReturn = new ArrayList<>(itemsToRemove);
    itemsToRemove.clear();
    updateSize();
    notifyItemRangeRemoved(beginIndex, endIndex);
    return itemsToReturn;
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    List<V> oldVms = vms();
    vms = new ArrayList<>(c);
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(oldVms, vms);
    } else {
      notifyDataSetChanged();
    }
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    this.observableAdapterDelegate.registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    this.observableAdapterDelegate.unregisterObserver(observer);
  }

  private void notifyDataSetChanged() {
    this.observableAdapterDelegate.notifyOnDataSetChanged();
  }

  private void notifyItemInserted(int position) {
    this.observableAdapterDelegate.notifyOnItemInserted(position);
  }

  private void notifyItemRangeInserted(int positionStart, int itemCount) {
    this.observableAdapterDelegate.notifyOnItemRangeInserted(positionStart, itemCount);
  }

  private void notifyItemRemoved(int position) {
    this.observableAdapterDelegate.notifyOnItemRemoved(position);
  }

  private void notifyItemRangeRemoved(int positionStart, int itemCount) {
    this.observableAdapterDelegate.notifyOnItemRangeRemoved(positionStart, itemCount);
  }

  private void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    this.observableAdapterDelegate.notifyOnDataSetChanged(oldItems, newVms);
  }

  private void notifyItemChanged(int position) {
    this.observableAdapterDelegate.notifyOnItemChanged(position);
  }

  //TODO test
  private void updateSize() {
    int newSize = vms.size();
    if (newSize != size) {
      if (size == 0 || newSize == 0) {
        isEmpty = vms.isEmpty();
        notifyPropertyChanged(BR.empty);
      }
      size = newSize;
      notifyPropertyChanged(BR.size);
    }
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BaseVmAdapter)) {
      return false;
    }
    BaseVmAdapter other = (BaseVmAdapter) obj;
    return this.vms.equals(other.vms);
  }

  @Override public int hashCode() {
    return this.vms.hashCode();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener<V> {

    @Override public void onItemRemoved(int position, V item) {
      BaseVmAdapter.this.updateSize();
      BaseVmAdapter.this.notifyItemRemoved(position);
    }
  }

  private class AdapterListIteratorListenerImpl extends AdapterIteratorListenerImpl implements
      AdapterListIterator.AdapterListIteratorListener<V> {

    @Override public void onItemAdded(int position, V item) {
      BaseVmAdapter.this.updateSize();
      BaseVmAdapter.this.notifyItemInserted(position);
    }

    @Override public void onItemChanged(int position, V item, V oldItem) {
      BaseVmAdapter.this.notifyItemChanged(position);
    }
  }
}
