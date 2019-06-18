package com.skiff2011.baseadapter.misc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import java.io.Serializable;

public final class SuperObservable<T extends BaseObservable> implements Serializable {
  @NonNull private T superObservable;
  @NonNull private BaseObservable dependantObservable;
  //equals null if deserialization occurred and DependantCallback should be added again to superObservable
  @Nullable transient private Boolean deserializationIndicator = false;

  public SuperObservable(@NonNull T superObservable,
      @NonNull BaseObservable dependantObservable) {
    this.superObservable = superObservable;
    this.dependantObservable = dependantObservable;
    chainDependantObservable();
  }

  public T getValue() {
    if (deserializationIndicator == null) {
      chainDependantObservable();
    }
    return superObservable;
  }

  private void chainDependantObservable() {
    superObservable.addOnPropertyChangedCallback(new DependantCallback());
  }

  private class DependantCallback extends Observable.OnPropertyChangedCallback {

    @Override public void onPropertyChanged(Observable sender, int propertyId) {
      dependantObservable.notifyPropertyChanged(propertyId);
    }
  }
}
