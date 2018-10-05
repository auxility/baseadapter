package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public abstract class ObservableAdapter<V extends VM> {

  private transient List<AdapterDataObserver<V>> observers = new ArrayList<>();

  public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    synchronized (this) {
      if (observers == null) {
        observers = new ArrayList<>();
      }
      if (!observers.contains(observer)) {
        observers.add(observer);
      }
    }
  }

  public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    synchronized (this) {
      if (observers != null) {
        observers.remove(observer);
      }
    }
  }

  public List<AdapterDataObserver<V>> getObservers() {
    return observers;
  }
}
