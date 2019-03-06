package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class ObservableAdapterImpl<V extends VM> implements ObservableAdapter<V> {

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

  public void notifyDataSetChanged() {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyDataSetChanged();
      }
    }
  }

  public void notifyItemInserted(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyItemInserted(position);
      }
    }
  }

  public void notifyItemRangeInserted(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyItemRangeInserted(positionStart, itemCount);
      }
    }
  }

  public void notifyItemRemoved(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyItemRemoved(position);
      }
    }
  }

  public void notifyItemRangeRemoved(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyItemRangeRemoved(positionStart, itemCount);
      }
    }
  }

  public void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyDataSetChanged(oldItems, newVms);
      }
    }
  }

  public void notifyItemChanged(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.notifyItemChanged(position);
      }
    }
  }
}
