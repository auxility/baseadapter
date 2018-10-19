package eu.theappshop.baseadapter.adapter;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseAdapter<V extends VM> extends ObservableAdapter<V>
    implements Adapter<V>, EndlessAdapter<V> {

  protected List<V> vms;
  private LoadMoreListener loadMoreListener;
  private boolean isLoading;
  private boolean hasLoadedAll;
  private int threshold = 5;

  protected transient PropertyChangeRegistry registry;

  public BaseAdapter(List<V> vms, LoadMoreListener loadMoreListener) {
    this.loadMoreListener = loadMoreListener;
    onNext(vms);
  }

  public BaseAdapter(List<V> vms) {
    onNext(vms);
  }

  public BaseAdapter() {
    vms = new ArrayList<>();
  }

  @Override
  public void onNext(List<V> newVms) {
    boolean wasEmpty = vms == null || isEmpty();
    List<V> oldVms = vms;
    this.vms = new ArrayList<>(newVms);
    for (AdapterDataObserver<V> observer : getObservers()) {
      observer.notifyDataSetChanged(oldVms, this.vms);
    }
    if (wasEmpty != isEmpty()) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void clear() {
    boolean wasEmpty = isEmpty();
    vms.clear();
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyDataSetChanged();
    }
    if (!wasEmpty) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void add(V item) {
    boolean wasEmpty = isEmpty();
    vms.add(item);
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyItemInserted(vms.size() - 1);
    }
    if (wasEmpty) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void add(int position, V item) {
    boolean wasEmpty = isEmpty();
    vms.add(position, item);
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyItemInserted(position);
    }
    if (wasEmpty) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void addAll(List<? extends V> list) {
    int previousLength = getItemCount();
    vms.addAll(list);
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyItemRangeInserted(previousLength, list.size());
    }
    if (previousLength == 0) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void addAll(List<? extends V> list, int position) {
    boolean wasEmpty = isEmpty();
    vms.addAll(position, list);
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyItemRangeInserted(position, list.size());
    }
    if (wasEmpty) {
      notifyPropertyChanged(BR.empty);
    }
  }

  @Override
  public void remove(V v) {
    boolean wasEmpty = isEmpty();
    int index = vms.indexOf(v);
    if (index != -1) {
      V removed = vms.remove(index);
      for (AdapterDataObserver observer : getObservers()) {
        observer.notifyItemRemoved(index);
      }
      if (removed != null && !wasEmpty && isEmpty()) {
        notifyPropertyChanged(BR.empty);
      }
    }
  }

  @Override
  public V remove(int index) {
    boolean wasEmpty = isEmpty();
    if (index != -1) {
      V v = vms.remove(index);
      for (AdapterDataObserver observer : getObservers()) {
        observer.notifyItemRemoved(index);
      }
      if (v != null && !wasEmpty && isEmpty()) {
        notifyPropertyChanged(BR.empty);
      }
      return v;
    }
    return null;
  }

  @Override
  public List<V> removeRange(int start, int end) {
    boolean wasEmpty = isEmpty();
    List<V> removedVms = vms.subList(start, end);
    List<V> returnValues = new ArrayList<>(removedVms);
    removedVms.clear();
    for (AdapterDataObserver observer : getObservers()) {
      observer.notifyItemRangeRemoved(start, end - start);
    }
    if (!wasEmpty && isEmpty()) {
      notifyPropertyChanged(BR.empty);
    }
    return returnValues;
  }

  @Override
  public void refresh() {
    onNext(vms);
  }


  @Override
  public int getItemCount() {
    return vms.size();
  }

  @Override
  public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    viewHolder.bindViewModel(vms.get(position));
  }

  @Override
  public int getItemViewType(int position) {
    return vms.get(position).getLayoutId();
  }

  @Bindable
  @Override
  public boolean isEmpty() {
    return vms.isEmpty();
  }

  @Override
  public <T extends VM> int getCountItemType(Class<T> clazz) {
    int count = 0;
    for (V v : vms) {
      if (clazz.isAssignableFrom(v.getClass())) {
        count++;
      }
    }
    return count;
  }

  @Override
  public List<V> getItems() {
    return vms;
  }

  @Override
  public V getItem(int position) {
    return vms.get(position);
  }

  @Override
  public int indexOf(V o) {
    return vms.indexOf(o);
  }

  @Override
  public int lastIndexOf(V o) {
    return vms.lastIndexOf(o);
  }

  @NonNull
  @Override
  public Iterator<V> iterator() {
    return vms.iterator();
  }

  @Override
  public int findFirstIndexOf(Class<? extends V> cls) {
    for (int i = 0; i < getItemCount(); i++) {
      if (getItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int findLastIndexOf(Class<? extends V> cls) {
    for (int i = getItemCount() - 1; i >= 0; i--) {
      if (getItem(i).getClass() == cls) {
        return i;
      }
    }
    return -1;
  }

  @Override public void loadMore() {
    if (loadMoreListener != null && !isLoading && !hasLoadedAll) {
      loadMoreListener.onLoadMore();
    }
  }

  @Override
  public void setLoading(boolean loading) {
    isLoading = loading;
  }

  @Override
  public boolean isLoading() {
    return isLoading;
  }

  @Override
  public boolean hasLoadedAll() {
    return hasLoadedAll;
  }

  public void setLoadedAll(boolean isLoadedAll) {
    this.hasLoadedAll = isLoadedAll;
  }

  @Override public int getThreshold() {
    return threshold;
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    if (registry == null) {
      registry = new PropertyChangeRegistry();
    }
    registry.add(callback);
  }

  @Override
  public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    if (registry != null) {
      registry.remove(callback);
    }
  }

  /**
   * Notifies listeners that all properties of this instance have changed.
   */
  public synchronized void notifyChange() {
    if (registry != null) {
      registry.notifyCallbacks(this, 0, null);
    }
  }

  /**
   * Notifies listeners that a specific property has changed. The getter for the property
   * that changes should be marked with {@link Bindable} to generate a field in
   * <code>BR</code> to be used as <code>fieldId</code>.
   *
   * @param fieldId The generated BR id for the Bindable field.
   */
  public void notifyPropertyChanged(int fieldId) {
    if (registry != null) {
      registry.notifyCallbacks(this, fieldId, null);
    }
  }

  public interface LoadMoreListener extends Serializable {
    void onLoadMore();
  }
}
