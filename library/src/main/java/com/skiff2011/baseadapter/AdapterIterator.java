package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;
import java.util.Iterator;

//TODO test
public class AdapterIterator<V extends VM> implements Iterator<V> {

  @NonNull private final Iterator<V> iterator;
  @NonNull private final AdapterIteratorListener<V> listener;
  protected V currentItem;
  protected int position = 0;

  public AdapterIterator(@NonNull Iterator<V> iterator,
      @NonNull AdapterIteratorListener<V> listener) {
    this.iterator = iterator;
    this.listener = listener;
  }

  @Override public boolean hasNext() {
    return this.iterator.hasNext();
  }

  @Override public V next() {
    currentItem = this.iterator.next();
    this.position++;
    return currentItem;
  }

  @Override public void remove() {
    this.iterator.remove();
    listener.onItemRemoved(position, currentItem);
  }

  public interface AdapterIteratorListener<V extends VM> {
    void onItemRemoved(int position, V item);
  }
}
