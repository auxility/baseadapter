package com.skiff2011.baseadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.item.Item;
import java.util.Iterator;

//TODO test
public class AdapterIterator<V extends Item> implements Iterator<V> {

  @NonNull private final Iterator<V> iterator;
  @NonNull private final AdapterIteratorListener<V> listener;
  protected V currentItem;
  protected int position = -1;

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
    this.position--;
  }

  public interface AdapterIteratorListener<V extends Item> {
    void onItemRemoved(int position, V item);
  }
}
