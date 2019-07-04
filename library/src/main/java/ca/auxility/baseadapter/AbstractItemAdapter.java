package ca.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import ca.auxility.baseadapter.item.Item;
import ca.auxility.baseadapter.misc.function.Predicate;
import ca.auxility.baseadapter.view.BaseViewHolder;
import java.util.Collection;
import java.util.ListIterator;

public abstract class AbstractItemAdapter<V extends Item> extends BaseObservable
    implements ItemAdapter<V> {

  public int indexOf(@NonNull V vm) {
    return items().indexOf(vm);
  }

  public int lastIndexOf(@NonNull V vm) {
    return items().lastIndexOf(vm);
  }

  public boolean contains(@NonNull V vm) {
    return items().contains(vm);
  }

  public boolean containsAll(@NonNull Collection<? extends V> c) {
    return items().containsAll(c);
  }

  @NonNull public ListIterator<V> listIterator() {
    return listIterator(0);
  }

  public boolean remove(@NonNull V vm) {
    int index = indexOf(vm);
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

  public void add(@NonNull V vm) {
    add(items().size(), vm);
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
