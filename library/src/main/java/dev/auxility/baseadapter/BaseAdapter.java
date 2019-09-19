package dev.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.misc.function.Predicate;
import dev.auxility.baseadapter.misc.iterator.AdapterIterator;
import dev.auxility.baseadapter.misc.iterator.AdapterListIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BaseAdapter<V extends Item> extends AbstractAdapter<V> {

  @NonNull private final ObservableAdapterImpl observableAdapterDelegate =
      new ObservableAdapterImpl();

  @NonNull private List<V> items;

  private int size;

  private boolean isEmpty;

  public BaseAdapter(@NonNull List<V> items) {
    this.items = items;
    updateSize();
  }

  public BaseAdapter() {
    this(new ArrayList<V>());
  }

  @Bindable @Override public int getSize() {
    return this.size;
  }

  @Bindable @Override public boolean isEmpty() {
    return isEmpty;
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.items.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(items.listIterator(index),
        new AdapterListIteratorListenerImpl(),
        index);
  }

  @NonNull @Override public V remove(int index) {
    V e = items.remove(index);
    updateSize();
    notifyItemRemoved(index);
    return e;
  }

  @Override public void clear(boolean withDiffUtil) {
    List<V> oldItems = items();
    this.items.clear();
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(oldItems, items);
    } else {
      notifyDataSetChanged();
    }
  }

  @Override public void add(int index, @NonNull V element) {
    items.add(index, element);
    updateSize();
    notifyItemInserted(index);
  }

  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    boolean returnValue = items.addAll(index, c);
    updateSize();
    notifyItemRangeInserted(index, c.size());
    return returnValue;
  }

  @NonNull @Override public V get(int index) {
    return items.get(index);
  }

  //to avoid items list modification
  @NonNull @Override public List<V> items() {
    return Collections.unmodifiableList(items);
  }

  @NonNull @Override public V set(int index, @NonNull V element) {
    V returnValue = items.set(index, element);
    notifyItemChanged(index);
    return returnValue;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    Iterator<V> iterator = items.iterator();
    List<V> olditems = new ArrayList<>(items);
    while (iterator.hasNext()) {
      V item = iterator.next();
      if (predicate.apply(item)) {
        iterator.remove();
      }
    }
    if (olditems.size() == items.size()) {
      return false;
    }
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(olditems, items);
    } else {
      notifyDataSetChanged();
    }
    return true;
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    List<V> itemsToRemove = items.subList(beginIndex, endIndex);
    List<V> itemsToReturn = new ArrayList<>(itemsToRemove);
    itemsToRemove.clear();
    updateSize();
    notifyItemRangeRemoved(beginIndex, endIndex);
    return itemsToReturn;
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    List<V> olditems = items();
    items = new ArrayList<>(c);
    updateSize();
    if (withDiffUtil) {
      notifyDataSetChanged(olditems, items);
    } else {
      notifyDataSetChanged();
    }
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver observer) {
    this.observableAdapterDelegate.registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver observer) {
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

  private void notifyDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newitems) {
    this.observableAdapterDelegate.notifyOnDataSetChanged(oldItems, newitems);
  }

  private void notifyItemChanged(int position) {
    this.observableAdapterDelegate.notifyOnItemChanged(position);
  }

  //TODO test
  private void updateSize() {
    int newSize = items.size();
    if (newSize != size) {
      if (size == 0 || newSize == 0) {
        isEmpty = items.isEmpty();
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
    if (!(obj instanceof BaseAdapter)) {
      return false;
    }
    BaseAdapter other = (BaseAdapter) obj;
    return this.items.equals(other.items);
  }

  @Override public int hashCode() {
    return this.items.hashCode();
  }

  @Override public void refresh() {
    notifyPropertyChanged(BR.size);
    notifyPropertyChanged(BR.empty);
    notifyDataSetChanged();
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener<V> {

    @Override public void onItemRemoved(int position, V item) {
      BaseAdapter.this.updateSize();
      BaseAdapter.this.notifyItemRemoved(position);
    }
  }

  private class AdapterListIteratorListenerImpl extends AdapterIteratorListenerImpl implements
      AdapterListIterator.AdapterListIteratorListener<V> {

    @Override public void onItemAdded(int position, V item) {
      BaseAdapter.this.updateSize();
      BaseAdapter.this.notifyItemInserted(position);
    }

    @Override public void onItemChanged(int position, V item, V oldItem) {
      BaseAdapter.this.notifyItemChanged(position);
    }
  }
}
