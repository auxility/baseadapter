package eu.theappshop.baseadapter.adapterv2;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterableVmAdapter<V extends VM> implements Adapter<V> {

  @NonNull private List<Filter<V>> filters;
  @NonNull private final Adapter<V> adapter;
  @NonNull private List<V> vms;

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters,
      @NonNull Adapter<V> adapter,
      @NonNull List<V> vms) {
    this.vms = vms;
    this.filters = filters;
    this.adapter = adapter;
    refresh();
  }

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters,
      @NonNull List<V> vms) {
    this(filters, new VmAdapter<V>(), vms);
  }

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters,
      @NonNull Adapter<V> adapter) {
    this(filters, adapter, new ArrayList<V>());
  }

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters) {
    this(filters, new VmAdapter<V>());
  }

  public FilterableVmAdapter() {
    this(new ArrayList<Filter<V>>());
  }

  @NonNull public List<Filter<V>> getFilters() {
    return new ArrayList<>(this.filters);
  }

  public void setFilters(@NonNull List<Filter<V>> filters) {
    this.filters = filters;
    refresh();
  }

  @Bindable @Override public int getSize() {
    return this.adapter.getSize();
  }

  @Bindable @Override public boolean isEmpty() {
    return this.adapter.isEmpty();
  }

  @Override public boolean contains(@NonNull V vm) {
    return this.vms.contains(vm);
  }

  @Override public boolean containsAll(@NonNull Collection<? extends V> c) {
    return this.vms.containsAll(c);
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.vms.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public V remove(int index) {
    //TODO implement
    return null;
  }

  @Override public boolean remove(@NonNull V vm) {
    //TODO implement
    return false;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate) {
    //TODO implement
    return false;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    //TODO implement
    return false;
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    //TODO implement
    return null;
  }

  @Override public void clear() {
    //TODO implement
  }

  @Override public void clear(boolean withDiffUtil) {
    //TODO implement
  }

  @Override public void add(@NonNull V vm) {
    //TODO implement
  }

  @Override public void add(int index, @NonNull V element) {
    //TODO implement
  }

  @Override public boolean addAll(@NonNull Collection<? extends V> c) {
    //TODO implement
    return false;
  }

  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    //TODO implement
    return false;
  }

  @NonNull @Override public V get(int index) {
    return this.vms.get(index);
  }

  @NonNull @Override public List<V> vms() {
    return Collections.unmodifiableList(this.vms);
  }

  @NonNull @Override public V set(int index, @NonNull V element) {
    //TODO implement
    return null;
  }

  @Override public void set(@NonNull Collection<? extends V> c) {
    //TODO implement
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    //TODO implement
  }

  @Override public int indexOf(@NonNull V vm) {
    return vms.indexOf(vm);
  }

  @Override public int lastIndexOf(@NonNull V vm) {
    return vms.lastIndexOf(vm);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return new AdapterListIterator<>(vms.listIterator(), new AdapterListIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(vms.listIterator(index), new AdapterListIteratorListenerImpl(),
        index);
  }

  @Override public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    this.adapter.bindViewHolder(viewHolder, position);
  }

  @Override public void refresh() {
    //TODO implement
  }

  @Override public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    this.adapter.addOnPropertyChangedCallback(callback);
  }

  @Override public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    this.adapter.removeOnPropertyChangedCallback(callback);
  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {
    this.adapter.registerObserver(observer);
  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {
    this.adapter.unregisterObserver(observer);
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener {

    @Override public void onItemRemoved(int position) {
      //TODO implement
    }
  }

  private class AdapterListIteratorListenerImpl implements
      AdapterListIterator.AdapterListIteratorListener {
    @Override public void onItemAdded(int position) {
      //TODO implement
    }

    @Override public void onItemChanged(int position) {
      //TODO implement
    }

    @Override public void onItemRemoved(int position) {
      //TODO implement
    }
  }
}
