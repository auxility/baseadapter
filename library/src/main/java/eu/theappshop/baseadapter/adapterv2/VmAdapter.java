package eu.theappshop.baseadapter.adapterv2;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class VmAdapter<V extends VM> extends BaseObservable implements Adapter<V> {

  @NonNull private final ObservableAdapterImpl<V> observableAdapterDelegate =
      new ObservableAdapterImpl<>();

  @NonNull protected List<V> vms;

  private int size;

  private boolean isEmpty;

  public VmAdapter(@NonNull List<V> vms) {
    this.vms = vms;
    updateSize();
  }

  public VmAdapter() {
    this(new ArrayList<V>());
  }

  @Override public int getSize() {
    return this.size;
  }

  @Override public boolean isEmpty() {
    return isEmpty;
  }

  @Override public boolean contains(@NonNull V vm) {
    return this.vms.contains(vm);
  }

  @Override public boolean containsAll(@NonNull Collection<? extends V> c) {
    return this.vms.containsAll(c);
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator(this.vms.iterator());
  }

  @NonNull @Override public V remove(int index) {
    V e = vms.remove(index);
    updateSize();
    notifyItemRemoved(index);
    return e;
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
    clearVms();
    notifyDataSetChanged();
  }

  @Override public void clear(boolean withDiffUtil) {
    if (withDiffUtil) {
      List<V> oldVms = vms();
      clearVms();
      notifyDataSetChanged(oldVms, vms);
    } else {
      clear();
    }
  }

  private void clearVms() {
    this.vms.clear();
    updateSize();
  }

  @Override public void add(@NonNull V vm) {
    add(vms.size(), vm);
  }

  @Override public void add(int index, @NonNull V element) {
    vms.add(index, element);
    updateSize();
    notifyItemInserted(index);
  }

  @Override public boolean addAll(@NonNull Collection<? extends V> c) {
    return addAll(vms.size(), c);
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

  @Override public int indexOf(@NonNull V vm) {
    return vms.indexOf(vm);
  }

  @Override public int lastIndexOf(@NonNull V vm) {
    return vms.lastIndexOf(vm);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return new AdapterListIterator(vms.listIterator());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator(vms.listIterator(), index);
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate) {
    Iterator<V> iterator = vms.iterator();
    boolean isChanged = false;
    while (iterator.hasNext()) {
      V vm = iterator.next();
      if (predicate.apply(vm)) {
        iterator.remove();
        isChanged = true;
      }
    }
    if (isChanged) {
      updateSize();
      notifyDataSetChanged();
    }
    return isChanged;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    if (withDiffUtil) {
      Iterator<V> iterator = vms.iterator();
      List<V> oldvms = new ArrayList<>(vms);
      List<Integer> removedIndexes = new ArrayList<>();
      int i = 0;
      while (iterator.hasNext()) {
        V vm = iterator.next();
        if (predicate.apply(vm)) {
          iterator.remove();
          removedIndexes.add(i);
        }
        i++;
      }
      if (removedIndexes.size() == 1) {
        notifyItemRemoved(removedIndexes.get(0));
      } else if (removedIndexes.size() > 1) {
        notifyDataSetChanged(oldvms, vms);
      }
      if (removedIndexes.size() > 0) {
        updateSize();
      }
      return removedIndexes.size() > 0;
    } else {
      return removeIf(predicate);
    }
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    List<V> itemsToRemove = vms.subList(beginIndex, endIndex);
    List<V> itemsToReturn = new ArrayList<>(itemsToRemove);
    itemsToRemove.clear();
    updateSize();
    notifyItemRangeRemoved(beginIndex, endIndex);
    return itemsToReturn;
  }

  @Override public void set(@NonNull Collection<? extends V> c) {
    setVms(c);
    notifyDataSetChanged();
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    if (withDiffUtil) {
      List<V> oldVms = vms();
      setVms(c);
      notifyDataSetChanged(oldVms, vms);
    } else {
      set(c);
    }
  }

  private void setVms(@NonNull Collection<? extends V> c) {
    vms = new ArrayList<>(c);
    updateSize();
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    this.observableAdapterDelegate.registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    this.observableAdapterDelegate.unregisterObserver(observer);
  }

  protected void notifyDataSetChanged() {
    this.observableAdapterDelegate.notifyDataSetChanged();
  }

  protected void notifyItemInserted(int position) {
    this.observableAdapterDelegate.notifyItemInserted(position);
  }

  protected void notifyItemRangeInserted(int positionStart, int itemCount) {
    this.observableAdapterDelegate.notifyItemRangeInserted(positionStart, itemCount);
  }

  protected void notifyItemRemoved(int position) {
    this.observableAdapterDelegate.notifyItemRemoved(position);
  }

  protected void notifyItemRangeRemoved(int positionStart, int itemCount) {
    this.observableAdapterDelegate.notifyItemRangeRemoved(positionStart, itemCount);
  }

  protected void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    this.observableAdapterDelegate.notifyDataSetChanged(oldItems, newVms);
  }

  protected void notifyItemChanged(int position) {
    this.observableAdapterDelegate.notifyItemChanged(position);
  }

  //TODO test
  private void updateSize() {
    int newSize = vms.size();
    if (newSize != size) {
      size = newSize;
      notifyPropertyChanged(BR.size);
      if (isEmpty != (size == 0)) {
        isEmpty = size == 0;
        notifyPropertyChanged(BR.empty);
      }
    }
  }

  @Override
  public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    viewHolder.bindViewModel(vms.get(position));
  }

  @Override public void refresh() {
    //Do Nothing
  }

  //TODO test
  class AdapterIterator implements Iterator<V> {

    private final Iterator<V> iterator;
    private int position = 0;

    private AdapterIterator(@NonNull Iterator<V> iterator) {
      this.iterator = iterator;
    }

    @Override public boolean hasNext() {
      return this.iterator.hasNext();
    }

    @Override public V next() {
      this.position++;
      return iterator.next();
    }

    @Override public void remove() {
      this.iterator.remove();
      updateSize();
      notifyItemRemoved(this.position);
    }
  }

  //TODO test
  class AdapterListIterator implements ListIterator<V> {

    private final ListIterator<V> iterator;
    private int position = 0;

    AdapterListIterator(@NonNull ListIterator<V> iterator) {
      this(iterator, 0);
    }

    AdapterListIterator(@NonNull ListIterator<V> iterator, int index) {
      this.iterator = iterator;
      this.position = index;
    }

    @Override public boolean hasNext() {
      return this.iterator.hasNext();
    }

    @Override public V next() {
      this.position++;
      return this.iterator.next();
    }

    @Override public boolean hasPrevious() {
      return this.iterator.hasPrevious();
    }

    @Override public V previous() {
      this.position--;
      return this.iterator.previous();
    }

    @Override public int nextIndex() {
      return this.iterator.nextIndex();
    }

    @Override public int previousIndex() {
      return this.iterator.previousIndex();
    }

    @Override public void remove() {
      this.iterator.remove();
      updateSize();
      notifyItemRemoved(this.position);
    }

    @Override public void set(V v) {
      this.iterator.set(v);
      notifyItemChanged(this.position);
    }

    @Override public void add(V v) {
      this.position++;
      this.iterator.add(v);
      updateSize();
      notifyItemInserted(this.position);
    }
  }
}
