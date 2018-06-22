package eu.theappshop.baseadapter.adapter;

import android.database.Observable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseAdapter<V extends VM> extends Observable<AdapterDataObserver>
    implements Adapter<V> {

  private List<V> vms;

  public BaseAdapter(List<V> vms) {
    this.vms = vms;
  }

  public BaseAdapter() {
    vms = new ArrayList<>();
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
  public void clear() {
    vms.clear();
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyDataSetChanged();
    }
  }

  @Override
  public void add(V item) {
    vms.add(item);
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyItemInserted(vms.size());
    }
  }

  @Override
  public void add(int position, V item) {
    vms.add(position, item);
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyItemInserted(position);
    }
  }

  @Override
  public void addAll(List<? extends V> list) {
    int previousLength = getItemCount();
    vms.addAll(list);
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyItemRangeInserted(previousLength, list.size());
    }
  }

  @Override
  public void addAll(List<? extends V> list, int position) {
    vms.addAll(position, list);
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyItemRangeInserted(position, list.size());
    }
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

  @Override
  public void remove(V v) {
    int index = vms.indexOf(v);
    if (index != -1) {
      vms.remove(index);
      for (AdapterDataObserver observer : mObservers) {
        observer.notifyItemRemoved(index);
      }
    }
  }

  @Override
  public V remove(int index) {
    if (index != -1) {
      V v = vms.remove(index);
      for (AdapterDataObserver observer : mObservers) {
        observer.notifyItemRemoved(index);
      }
      return v;
    }
    return null;
  }

  @Override
  public void removeRange(int start, int end) {
    vms.subList(start, end).clear();
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyItemRangeRemoved(start, end - start);
    }
  }

  @Override
  public void refresh() {
    for (AdapterDataObserver observer : mObservers) {
      observer.notifyDataSetChanged();
    }
  }

  @Override
  public void setVMs(List<V> vms) {
    this.vms = vms;
  }
}
