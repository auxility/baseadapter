package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.item.Item;
import ca.auxility.baseadapter.misc.SuperObservable;

abstract public class AbstractItemAdapterDecorator<V extends Item> extends AbstractItemAdapter<V> {

  @NonNull private SuperObservable<AbstractItemAdapter<V>> decoratedAdapter;

  public AbstractItemAdapterDecorator(
      @NonNull AbstractItemAdapter<V> decoratedAdapter) {
    this.decoratedAdapter = new SuperObservable<>(decoratedAdapter, this);
  }

  protected AbstractItemAdapter<V> getAdapter() {
    return decoratedAdapter.getValue();
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver observer) {
    getAdapter().registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver observer) {
    getAdapter().unregisterObserver(observer);
  }
}
