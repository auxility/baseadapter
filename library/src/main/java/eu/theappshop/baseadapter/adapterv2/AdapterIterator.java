package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.Iterator;

//TODO test
public class AdapterIterator<V extends VM> implements Iterator<V> {

  @NonNull private final Iterator<V> iterator;
  @NonNull private final AdapterIteratorListener listener;
  protected int position = 0;

  public AdapterIterator(@NonNull Iterator<V> iterator,
      @NonNull AdapterIteratorListener listener) {
    this.iterator = iterator;
    this.listener = listener;
  }

  @Override public boolean hasNext() {
    return this.iterator.hasNext();
  }

  @Override public V next() {
    V vm = this.iterator.next();
    this.position++;
    return vm;
  }

  @Override public void remove() {
    this.iterator.remove();
    listener.onItemRemoved(position);
  }

  public interface AdapterIteratorListener {
    void onItemRemoved(int position);
  }
}
