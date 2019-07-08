package dev.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.misc.function.Predicate;
import dev.auxility.baseadapter.view.BaseViewHolder;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public interface Adapter<V extends Item> extends Observable,
    Serializable,
    ObservableAdapter,
    Iterable<V> {

  int indexOf(@NonNull V item);

  int lastIndexOf(@NonNull V item);

  boolean contains(@NonNull V item);

  boolean containsAll(@NonNull Collection<? extends V> c);

  @NonNull ListIterator<V> listIterator();

  boolean remove(@NonNull V item);

  void clear();

  void add(@NonNull V item);

  boolean addAll(@NonNull Collection<? extends V> c);

  boolean removeIf(@NonNull Predicate<V> predicate);

  void set(@NonNull Collection<? extends V> c);

  void bindViewHolder(BaseViewHolder<V> viewHolder, int position);

  @Bindable int getSize();

  @Bindable boolean isEmpty();

  void refresh();

  @NonNull ListIterator<V> listIterator(int index);

  @NonNull V get(int index);

  @NonNull List<V> items();

  //CRUD

  //remove methods
  @NonNull V remove(int index);

  boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil);

  List<V> removeRange(int beginIndex, int endIndex);

  void clear(boolean withDiffUtil);

  //add methods

  void add(int index, @NonNull V element);

  boolean addAll(int index, @NonNull Collection<? extends V> c);

  //set methods

  @NonNull V set(int index, @NonNull V element);

  void set(@NonNull Collection<? extends V> c, boolean withDiffUtil);
}
