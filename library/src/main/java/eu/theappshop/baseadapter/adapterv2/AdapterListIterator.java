package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ListIterator;

public class AdapterListIterator<V extends VM> extends AdapterIterator<V>
    implements ListIterator<V> {

  @NonNull private final ListIterator<V> listIterator;
  @NonNull private final AdapterListIteratorListener listListener;

  public AdapterListIterator(@NonNull ListIterator<V> iterator,
      @NonNull
          AdapterListIteratorListener listener) {
    super(iterator, listener);
    this.listIterator = iterator;
    this.listListener = listener;
  }

  public AdapterListIterator(@NonNull ListIterator<V> iterator,
      @NonNull
          AdapterListIteratorListener listener, int index) {
    this(iterator, listener);
    this.position = index;
  }

  @Override public boolean hasPrevious() {
    return this.listIterator.hasPrevious();
  }

  @Override public V previous() {
    V vm = this.listIterator.previous();
    this.position--;
    return vm;
  }

  @Override public int nextIndex() {
    return this.listIterator.nextIndex();
  }

  @Override public int previousIndex() {
    return this.listIterator.previousIndex();
  }

  @Override public void set(V v) {
    this.listIterator.set(v);
    listListener.onItemChanged(this.position);
  }

  @Override public void add(V v) {
    this.listIterator.add(v);
    listListener.onItemAdded(this.position);
  }

  public interface AdapterListIteratorListener extends AdapterIteratorListener {

    void onItemAdded(int position);

    void onItemChanged(int position);
  }
}
