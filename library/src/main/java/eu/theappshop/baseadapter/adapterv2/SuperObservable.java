package eu.theappshop.baseadapter.adapterv2;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
