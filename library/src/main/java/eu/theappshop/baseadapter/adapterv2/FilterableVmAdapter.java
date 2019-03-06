package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterableVmAdapter<V extends VM> implements Adapter<V> {

  @NonNull private final List<Filter<V>> filters;
  @NonNull private final Adapter<V> adapter;
  @NonNull private List<V> vms;

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters,
      @NonNull Adapter<V> adapter,
      @NonNull List<V> vms) {
    this.filters = filters;
    this.adapter = adapter;
    this.vms = vms;
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

  @Override public int getSize() {
    return adapter.getSize();
  }

  @Override public boolean isEmpty() {
    return adapter.isEmpty();
  }

  @Override public boolean contains(@NonNull V vm) {
    return vms.contains(vm);
  }

  @Override public boolean containsAll(@NonNull Collection<? extends V> c) {
    return vms.containsAll(c);
  }

  @NonNull @Override public Iterator<V> iterator() {
    return null;
  }

  @NonNull @Override public V remove(int index) {
    return null;
  }

  @Override public boolean remove(@NonNull V vm) {
    return false;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate) {
    return false;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    return false;
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    return null;
  }

  @Override public void clear() {

  }

  @Override public void clear(boolean withDiffUtil) {

  }

  @Override public void add(@NonNull V vm) {

  }

  @Override public void add(int index, @NonNull V element) {

  }

  @Override public boolean addAll(@NonNull Collection<? extends V> c) {
    return false;
  }

  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    return false;
  }

  @NonNull @Override public V get(int index) {
    return null;
  }

  @NonNull @Override public List<V> vms() {
    return null;
  }

  @NonNull @Override public V set(int index, @NonNull V element) {
    return null;
  }

  @Override public void set(@NonNull Collection<? extends V> c) {

  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {

  }

  @Override public int indexOf(@NonNull V vm) {
    return 0;
  }

  @Override public int lastIndexOf(@NonNull V vm) {
    return 0;
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return null;
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return null;
  }

  @Override public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {

  }

  @Override public void refresh() {

  }

  @Override public void notifyDataSetChanged() {

  }

  @Override public void notifyItemInserted(int position) {

  }

  @Override public void notifyItemRangeInserted(int positionStart, int itemCount) {

  }

  @Override public void notifyItemRemoved(int position) {

  }

  @Override public void notifyItemRangeRemoved(int positionStart, int itemCount) {

  }

  @Override public void notifyDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {

  }

  @Override public void notifyItemChanged(int position) {

  }

  @Override public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

  }

  @Override public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

  }

  @Override public void registerObserver(@NonNull AdapterDataObserver<V> observer) {

  }

  @Override public void unregisterObserver(@NonNull AdapterDataObserver<V> observer) {

  }
}
