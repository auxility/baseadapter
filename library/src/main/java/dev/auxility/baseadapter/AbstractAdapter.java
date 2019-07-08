package dev.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.misc.function.Predicate;
import dev.auxility.baseadapter.view.BaseViewHolder;
import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractAdapter<V extends Item> extends BaseObservable
    implements Adapter<V> {

  public int indexOf(@NonNull V item) {
    return items().indexOf(item);
  }

  public int lastIndexOf(@NonNull V item) {
    return items().lastIndexOf(item);
  }

  public boolean contains(@NonNull V item) {
    return items().contains(item);
  }

  public boolean containsAll(@NonNull Collection<? extends V> c) {
    return items().containsAll(c);
  }

  @NonNull public ListIterator<V> listIterator() {
    return listIterator(0);
  }

  public boolean remove(@NonNull V item) {
    int index = indexOf(item);
    if (index < 0) {
      return false;
    } else {
      remove(index);
      return true;
    }
  }

  public void clear() {
    clear(false);
  }

  public void add(@NonNull V item) {
    add(items().size(), item);
  }

  public boolean addAll(@NonNull Collection<? extends V> c) {
    return addAll(items().size(), c);
  }

  public boolean removeIf(@NonNull Predicate<V> predicate) {
    return removeIf(predicate, false);
  }

  public void set(@NonNull Collection<? extends V> c) {
    set(c, false);
  }

  public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
    viewHolder.bindViewModel(get(position));
  }

  public @Bindable boolean isEmpty() {
    return getSize() == 0;
  }

  public void refresh() {

  }
}
