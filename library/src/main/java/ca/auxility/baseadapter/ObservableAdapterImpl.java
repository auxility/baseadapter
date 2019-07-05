package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ObservableAdapterImpl implements ObservableAdapter, AdapterDataObserver {

  private transient List<AdapterDataObserver> observers = new ArrayList<>();

  @Override public void registerObserver(@NonNull AdapterDataObserver observer) {
    synchronized (this) {
      if (observers == null) {
        observers = new ArrayList<>();
      }
      if (!observers.contains(observer)) {
        observers.add(observer);
      }
    }
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver observer) {
    synchronized (this) {
      if (observers != null) {
        observers.remove(observer);
      }
    }
  }

  @Override public void notifyOnDataSetChanged() {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnDataSetChanged();
      }
    }
  }

  @Override public void notifyOnItemInserted(int position) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnItemInserted(position);
      }
    }
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnItemRangeInserted(positionStart, itemCount);
      }
    }
  }

  @Override public void notifyOnItemRemoved(int position) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnItemRemoved(position);
      }
    }
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnItemRangeRemoved(positionStart, itemCount);
      }
    }
  }

  @Override public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newItems) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnDataSetChanged(oldItems, newItems);
      }
    }
  }

  @Override public void notifyOnItemChanged(int position) {
    if (observers != null) {
      for (AdapterDataObserver observer : observers) {
        observer.notifyOnItemChanged(position);
      }
    }
  }
}
