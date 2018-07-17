package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableAdapter {

  private final String LOCK = "Some object that can be serialized";
  private transient List<AdapterDataObserver> observers = new ArrayList<>();

  public void registerObserver(@NonNull AdapterDataObserver observer) {
    synchronized (LOCK) {
      if (observers == null) {
        observers = new ArrayList<>();
      }
      if (!observers.contains(observer)) {
        observers.add(observer);
      }
    }
  }

  public void unregisterObserver(@NonNull AdapterDataObserver observer) {
    synchronized (LOCK) {
      if (observers != null) {
        observers.remove(observer);
      }
    }
  }

  public List<AdapterDataObserver> getObservers() {
    return observers;
  }
}
