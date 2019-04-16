package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
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

  @Override public void onNotifyDataSetChanged() {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyDataSetChanged();
      }
    }
  }

  @Override public void onNotifyItemInserted(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyItemInserted(position);
      }
    }
  }

  @Override public void onNotifyItemRangeInserted(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyItemRangeInserted(positionStart, itemCount);
      }
    }
  }

  @Override public void onNotifyItemRemoved(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyItemRemoved(position);
      }
    }
  }

  @Override public void onNotifyItemRangeRemoved(int positionStart, int itemCount) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyItemRangeRemoved(positionStart, itemCount);
      }
    }
  }

  @Override public void onNotifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyDataSetChanged(oldItems, newVms);
      }
    }
  }

  @Override public void onNotifyItemChanged(int position) {
    if (observers != null) {
      for (AdapterDataObserver<V> observer : observers) {
        observer.onNotifyItemChanged(position);
      }
    }
  }
}
