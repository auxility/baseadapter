package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import ca.auxility.baseadapter.item.Item;
import ca.auxility.baseadapter.misc.ListUtils;
import ca.auxility.baseadapter.misc.function.Predicate;
import ca.auxility.baseadapter.misc.function.SerializablePredicate;
import ca.auxility.baseadapter.misc.iterator.AdapterIterator;
import ca.auxility.baseadapter.misc.iterator.AdapterListIterator;
import ca.auxility.baseadapter.view.BaseViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterableAdapter<V extends Item> extends AbstractAdapterDecorator<V> {

  @NonNull private SerializablePredicate<V> filter;
  @NonNull private List<V> items;

  public FilterableAdapter(
      @NonNull AbstractAdapter<V> adapter,
      @NonNull List<V> items,
      @NonNull SerializablePredicate<V> filter) {
    super(adapter);
    this.items = items;
    this.filter = filter;
    refresh();
  }

  public FilterableAdapter(
      @NonNull List<V> items,
      @NonNull SerializablePredicate<V> filter) {
    this(new BaseAdapter<>(ListUtils.filter(items, filter)), items, filter);
  }

  public FilterableAdapter(
      @NonNull AbstractAdapter<V> adapter, @NonNull SerializablePredicate<V> filter) {
    this(adapter, new ArrayList<V>(), filter);
  }

  public FilterableAdapter(
      @NonNull SerializablePredicate<V> filter) {
    this(new BaseAdapter<V>(), filter);
  }

  public FilterableAdapter() {
    this(new SerializablePredicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return true;
      }
    });
  }

  @NonNull public SerializablePredicate<V> getFilter() {
    return filter;
  }

  public void setFilter(@NonNull SerializablePredicate<V> filter) {
    this.filter = filter;
    refresh();
  }

  @Bindable @Override public int getSize() {
    return getAdapter().getSize();
  }

  @Bindable @Override public boolean isEmpty() {
    return getAdapter().isEmpty();
  }

  @NonNull @Override public Iterator<V> iterator() {
    return new AdapterIterator<>(this.items.iterator(), new AdapterIteratorListenerImpl());
  }

  @NonNull @Override public V remove(int index) {
    V item = items.remove(index);
    if (filter.apply(item)) {
      getAdapter().remove(item);
    }
    return item;
  }

  @Override public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
    Iterator<V> iterator = items.iterator();
    int prevSize = items.size();
    while (iterator.hasNext()) {
      V item = iterator.next();
      if (predicate.apply(item)) {
        iterator.remove();
      }
    }
    if (items.size() == prevSize) {
      return false;
    }
    getAdapter().set(ListUtils.filter(items, filter), withDiffUtil);
    return true;
  }

  @Override public List<V> removeRange(int beginIndex, int endIndex) {
    List<V> itemsToRemove = items.subList(beginIndex, endIndex);
    List<V> itemsToReturn = new ArrayList<>(itemsToRemove);
    itemsToRemove.clear();
    V firstitem = ListUtils.first(itemsToReturn, new Predicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return filter.apply(object);
      }
    });
    V lastitem = ListUtils.last(itemsToReturn, new Predicate<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return filter.apply(object);
      }
    });
    if (firstitem != null && lastitem != null) {
      if (firstitem == lastitem) {
        getAdapter().remove(firstitem);
      } else {
        getAdapter()
            .removeRange(getAdapter().indexOf(firstitem),
                getAdapter().indexOf(lastitem) + 1);
      }
    }
    return itemsToReturn;
  }

  //TODO remove and use remove range
  @Override public void clear(boolean withDiffUtil) {
    this.items.clear();
    getAdapter().clear(withDiffUtil);
  }

  //TODO optimize
  @Override public void add(int index, @NonNull V element) {
    this.items.add(index, element);
    if (filter.apply(element)) {
      if (index == this.items.size()) {
        getAdapter().add(element);
      } else {
        refresh();
      }
    }
  }

  //TODO optimize
  @Override public boolean addAll(int index, @NonNull Collection<? extends V> c) {
    List<V> newitems = new ArrayList<>(c);
    boolean returnValue = this.items.addAll(index, newitems);
    if (index == this.items.size()) {
      List<V> newFiltereditems = ListUtils.filter(newitems, filter);
      getAdapter().addAll(newFiltereditems);
    } else {
      refresh();
    }
    return returnValue;
  }

  @NonNull @Override public V get(int index) {
    return getAdapter().get(index);
  }

  @NonNull @Override public List<V> items() {
    return Collections.unmodifiableList(this.items);
  }

  //TODO optimize
  @NonNull @Override public V set(int index, @NonNull V element) {
    V previtem = this.items.set(index, element);
    if (filter.apply(previtem) || filter.apply(element)) {
      refresh();
    }
    return previtem;
  }

  @Override public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
    List<V> newitems = new ArrayList<>(c);
    List<V> filtereditems = ListUtils.filter(newitems, filter);
    this.items = newitems;
    getAdapter().set(filtereditems, withDiffUtil);
  }

  @NonNull @Override public ListIterator<V> listIterator() {
    return new AdapterListIterator<>(items.listIterator(), new AdapterListIteratorListenerImpl());
  }

  @NonNull @Override public ListIterator<V> listIterator(int index) {
    return new AdapterListIterator<>(items.listIterator(index),
        new AdapterListIteratorListenerImpl(),
        index);
  }

  @Override public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    getAdapter().bindViewHolder(viewHolder, position);
  }

  @Override public void refresh() {
    getAdapter().set(ListUtils.filter(this.items, filter));
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof FilterableAdapter)) {
      return false;
    }
    FilterableAdapter other = (FilterableAdapter) obj;
    return this.items.equals(other.items) && getAdapter().equals(
        other.getAdapter());
  }

  @Override public int hashCode() {
    return this.items.hashCode() * 1000 + getAdapter().hashCode();
  }

  @Override public void notifyPropertyChanged(int fieldId) {
    super.notifyPropertyChanged(fieldId);
  }

  private class AdapterIteratorListenerImpl implements AdapterIterator.AdapterIteratorListener<V> {

    //TODO optimize?
    @Override public void onItemRemoved(int position, V item) {
      if (filter.apply(item)) {
        getAdapter().remove(item);
      }
    }
  }

  private class AdapterListIteratorListenerImpl extends AdapterIteratorListenerImpl implements
      AdapterListIterator.AdapterListIteratorListener<V> {

    //TODO optimize?
    @Override public void onItemAdded(int position, V item) {
      if (filter.apply(item)) {
        refresh();
      }
    }

    //TODO optimize?
    @Override public void onItemChanged(int position, V item, V oldItem) {
      boolean itemFiltered = filter.apply(item);
      boolean prevFiltered = filter.apply(oldItem);
      if (itemFiltered && prevFiltered) {
        getAdapter().set(getAdapter().indexOf(oldItem), item);
      } else if (itemFiltered) {
        refresh();
      } else if (prevFiltered) {
        getAdapter().remove(oldItem);
      }
    }
  }
}
