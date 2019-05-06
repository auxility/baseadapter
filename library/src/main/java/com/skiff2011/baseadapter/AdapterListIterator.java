package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;
import java.util.ListIterator;

public class AdapterListIterator<V extends VM> extends AdapterIterator<V>
    implements ListIterator<V> {

  @NonNull private final ListIterator<V> listIterator;
  @NonNull private final AdapterListIteratorListener<V> listListener;

  public AdapterListIterator(@NonNull ListIterator<V> iterator,
      @NonNull
          AdapterListIteratorListener<V> listener) {
    super(iterator, listener);
    this.listIterator = iterator;
    this.listListener = listener;
  }

  public AdapterListIterator(@NonNull ListIterator<V> iterator,
      @NonNull
          AdapterListIteratorListener<V> listener, int index) {
    this(iterator, listener);
    this.position = index;
  }

  @Override public boolean hasPrevious() {
    return this.listIterator.hasPrevious();
  }

  @Override public V previous() {
    currentItem = this.listIterator.previous();
    this.position--;
    return currentItem;
  }

  @Override public int nextIndex() {
    return this.listIterator.nextIndex();
  }

  @Override public int previousIndex() {
    return this.listIterator.previousIndex();
  }

  @Override public void set(V v) {
    this.listIterator.set(v);
    listListener.onItemChanged(this.position, v, currentItem);
  }

  @Override public void add(V v) {
    this.listIterator.add(v);
    listListener.onItemAdded(this.position, currentItem);
  }

  public interface AdapterListIteratorListener<V extends VM> extends AdapterIteratorListener<V> {

    void onItemAdded(int position, V item);

    void onItemChanged(int position, V item, V oldItem);
  }
}
