package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public abstract class ObservableAdapter<V extends VM> implements Adapter<V> {

  final List<AdapterDataObserver> observers = new ArrayList<>();

  @Override public void registerObserver(@NonNull AdapterDataObserver observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver observer) {
    observers.remove(observer);
  }
}
