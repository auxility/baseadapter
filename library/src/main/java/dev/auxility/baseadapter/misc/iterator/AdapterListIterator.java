package dev.auxility.baseadapter.misc.iterator;

import androidx.annotation.NonNull;
import dev.auxility.baseadapter.item.Item;
import java.util.ListIterator;

public class AdapterListIterator<V extends Item> extends AdapterIterator<V>
    implements ListIterator<V> {

  @NonNull private final ListIterator<V> listIterator;
  @NonNull private final AdapterListIteratorListener<V> listListener;

  private boolean forwardDirection = false;

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
    this.position = index - 1;
  }

  @Override public boolean hasPrevious() {
    return this.listIterator.hasPrevious();
  }

  @Override public V previous() {
    forwardDirection = false;
    currentItem = this.listIterator.previous();
    this.position--;
    return currentItem;
  }

  @Override public V next() {
    forwardDirection = true;
    return super.next();
  }

  @Override public int nextIndex() {
    return this.listIterator.nextIndex();
  }

  @Override public int previousIndex() {
    return this.listIterator.previousIndex();
  }

  @Override public void set(V v) {
    this.listIterator.set(v);
    int index;
    if (forwardDirection) {
      index = position;
    } else {
      index = position + 1;
    }
    listListener.onItemChanged(index, v, currentItem);
  }

  @Override public void add(V v) {
    this.listIterator.add(v);
    this.position++;
    listListener.onItemAdded(this.position, v);
  }

  public interface AdapterListIteratorListener<V extends Item> extends AdapterIteratorListener<V> {

    void onItemAdded(int position, V item);

    void onItemChanged(int position, V item, V oldItem);
  }
}
