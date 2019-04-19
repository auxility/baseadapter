package eu.theappshop.baseadapter.adapterv2;

import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import eu.theappshop.baseadapter.vm.VM;

abstract public class AbstractVmAdapterDecorator<V extends VM> extends AbstractVmAdapter<V> {

  @NonNull private AbstractVmAdapter<V> decoratedAdapter;

  //equals null if deserialization occurred and DependantCallback should be added again to superObservable
  @Nullable transient private Boolean deserializationIndicator = false;

  public AbstractVmAdapterDecorator(
      @NonNull AbstractVmAdapter<V> decoratedAdapter) {
    this.decoratedAdapter = decoratedAdapter;
  }

  public AbstractVmAdapter<V> getAdapter() {
    if (deserializationIndicator == null) {
      deserializationIndicator = false;
      chainDependantObservable();
    }
    return decoratedAdapter;
  }

  private void chainDependantObservable() {
    decoratedAdapter.addOnPropertyChangedCallback(new DependantCallback());
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    getAdapter().registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    getAdapter().unregisterObserver(observer);
  }

  private class DependantCallback extends Observable.OnPropertyChangedCallback {

    @Override public void onPropertyChanged(Observable sender, int propertyId) {
      AbstractVmAdapterDecorator.this.notifyPropertyChanged(propertyId);
    }
  }
}
