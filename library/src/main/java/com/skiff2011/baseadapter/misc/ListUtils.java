package com.skiff2011.baseadapter.misc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.skiff2011.baseadapter.misc.function.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class ListUtils {
  @NonNull
  public static <T> List<T> filter(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
    List<T> filteredList = new ArrayList<>();
    for (T item : list) {
      if (predicate.apply(item)) {
        filteredList.add(item);
      }
    }
    return filteredList;
  }

  public static <T> int firstIndex(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
    return findIndex(list.iterator(), predicate);
  }

  public static <T> int lastIndex(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
    int index = findIndex(new ReversedListIterator<>(list.listIterator(list.size())), predicate);
    if (index == -1) {
      return index;
    } else {
      return list.size() - index - 1;
    }
  }

  private static <T> int findIndex(@NonNull Iterator<T> iterator, @NonNull Predicate<T> predicate) {
    int i = -1;
    while (iterator.hasNext()) {
      i++;
      T item = iterator.next();
      if (predicate.apply(item)) {
        return i;
      }
    }
    return -1;
  }

  @Nullable public static <T> T first(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
    return find(list.iterator(), predicate);
  }

  @Nullable public static <T> T last(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
    return find(new ReversedListIterator<>(list.listIterator(list.size())), predicate);
  }

  @Nullable private static <T> T find(@NonNull Iterator<T> iterator, @NonNull Predicate<T> predicate) {
    while (iterator.hasNext()) {
      T item = iterator.next();
      if (predicate.apply(item)) {
        return item;
      }
    }
    return null;
  }

  static class ReversedListIterator<T> implements ListIterator<T> {

    private @NonNull ListIterator<T> listIterator;

    ReversedListIterator(@NonNull ListIterator<T> listIterator) {
      this.listIterator = listIterator;
    }

    @Override public boolean hasNext() {
      return this.listIterator.hasPrevious();
    }

    @Override public T next() {
      return this.listIterator.previous();
    }

    @Override public boolean hasPrevious() {
      return this.listIterator.hasNext();
    }

    @Override public T previous() {
      return this.listIterator.next();
    }

    @Override public int nextIndex() {
      return this.listIterator.previousIndex();
    }

    @Override public int previousIndex() {
      return this.listIterator.nextIndex();
    }

    @Override public void remove() {
      this.listIterator.remove();
    }

    @Override public void set(T t) {
      this.listIterator.set(t);
    }

    @Override public void add(T t) {
      throw new UnsupportedOperationException();
    }
  }
}
