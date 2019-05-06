package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.misc.SuperObservable;
import com.skiff2011.baseadapter.vm.VM;

abstract public class AbstractVmAdapterDecorator<V extends VM> extends AbstractVmAdapter<V> {

  @NonNull private SuperObservable<AbstractVmAdapter<V>> decoratedAdapter;

  public AbstractVmAdapterDecorator(
      @NonNull AbstractVmAdapter<V> decoratedAdapter) {
    this.decoratedAdapter = new SuperObservable<>(decoratedAdapter, this);
  }

  protected AbstractVmAdapter<V> getAdapter() {
    return decoratedAdapter.getValue();
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    getAdapter().registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    getAdapter().unregisterObserver(observer);
  }
}
