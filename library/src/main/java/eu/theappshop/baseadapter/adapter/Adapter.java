package eu.theappshop.baseadapter.adapter;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface Adapter<V extends VM> extends Serializable, Iterable<V>, Observable {

  int getItemCount();

  void bindViewHolder(BaseViewHolder<V> viewHolder, int position);

  int getItemViewType(int position);

  @Bindable
  boolean isEmpty();

  /**
   * @deprecated Use kotlin iterator extensions instead
   */
  @Deprecated
  <T extends VM> int getCountItemType(Class<T> clazz);

  void clear();

  void add(V item);

  void add(int position, V item);

  V set(int position, V item);

  void addAll(List<? extends V> list);

  void addAll(List<? extends V> list, int position);

  List<V> getItems();

  V getItem(int index);

  int indexOf(V o);

  int lastIndexOf(V o);

  Iterator<V> iterator();

  /**
   * @deprecated Use kotlin iterator extensions instead
   */
  @Deprecated
  int findFirstIndexOf(Class<? extends V> cls);

  /**
   * @deprecated Use kotlin iterator extensions instead
   */
  @Deprecated
  int findLastIndexOf(Class<? extends V> cls);

  void remove(V v);

  V remove(int index);

  List<V> removeRange(int start, int end);

  void registerObserver(@NonNull AdapterDataObserver<V> observer);

  void unregisterObserver(@NonNull AdapterDataObserver<V> observer);

  void refresh();

  /**
   * Sets a new List of VMs to this adapter. Intended to be called inside a reactive pipeline.
   * @param newVms vms
   */
  void onNext(final List<V> newVms);
}
