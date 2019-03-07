package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class FilterableVmAdapter<V extends VM> extends VmAdapter<V> {

  @NonNull private final List<Filter<V>> filters;
  @NonNull private final Adapter<V> adapter;

  public FilterableVmAdapter(
      @NonNull List<Filter<V>> filters,
      @NonNull Adapter<V> adapter,
      @NonNull List<V> vms) {
    super(vms);
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

  @Override public int getSize() {
    return adapter.getSize();
  }

  @Override public boolean isEmpty() {
    return adapter.isEmpty();
  }

  @NonNull @Override public V remove(int index) {
    return super.remove(index);
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    return super.removeIf(predicate, withDiffUtil);
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate) {
    return super.removeIf(predicate);
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    return super.removeRange(beginIndex, endIndex);
  }
}
