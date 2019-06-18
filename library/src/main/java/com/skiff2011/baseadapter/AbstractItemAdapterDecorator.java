package com.skiff2011.baseadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.misc.SuperObservable;

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
