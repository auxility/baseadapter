package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class ObservableAdapterImpl<V extends VM>
    implements ObservableAdapter<V>, AdapterDataObserver<V> {

  private transient List<AdapterDataObserver<V>> observers = new ArrayList<>();

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    synchronized (this) {
      if (observers == null) {
        observers = new ArrayList<>();
      }
      if (!observers.contains(observer)) {
        observers.add(observer);
      }
    }
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    synchronized (this) {
      if (observers != null) {
        observers.remove(observer);
      }
    }
  }

  @Override public void notifyOnDataSetChanged() {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnDataSetChanged();
      }
    }
  }

  @Override public void notifyOnItemInserted(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnItemInserted(position);
      }
    }
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnItemRangeInserted(positionStart, itemCount);
      }
    }
  }

  @Override public void notifyOnItemRemoved(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnItemRemoved(position);
      }
    }
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnItemRangeRemoved(positionStart, itemCount);
      }
    }
  }

  @Override public void notifyOnDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnDataSetChanged(oldItems, newVms);
      }
    }
  }

  @Override public void notifyOnItemChanged(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyOnItemChanged(position);
      }
    }
  }
}
