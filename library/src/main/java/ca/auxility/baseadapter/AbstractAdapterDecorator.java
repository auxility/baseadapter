package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.item.Item;
import ca.auxility.baseadapter.misc.SuperObservable;

abstract public class AbstractAdapterDecorator<V extends Item> extends AbstractAdapter<V> {

  @NonNull private SuperObservable<AbstractAdapter<V>> decoratedAdapter;

  public AbstractAdapterDecorator(
      @NonNull AbstractAdapter<V> decoratedAdapter) {
    this.decoratedAdapter = new SuperObservable<>(decoratedAdapter, this);
  }

  protected AbstractAdapter<V> getAdapter() {
    return decoratedAdapter.getValue();
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver observer) {
    getAdapter().registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver observer) {
    getAdapter().unregisterObserver(observer);
  }
}
