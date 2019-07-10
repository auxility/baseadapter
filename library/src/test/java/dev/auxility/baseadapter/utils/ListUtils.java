package dev.auxility.baseadapter.utils;

import dev.auxility.baseadapter.misc.function.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ListUtils {
  public static <T> List<T> generateList(int count, Mapper<Integer, T> mapper) {
    List<T> list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      list.add(mapper.map(i));
    }
    return list;
  }

  @SafeVarargs public static <T> List<T> listOf(T... items) {
    return new ArrayList<>(Arrays.asList(items));
  }

  public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    List<T> filteredList = new ArrayList<>();
    for (T item : list) {
      if (predicate.apply(item)) {
        filteredList.add(item);
      }
    }
    return filteredList;
  }

  public interface Mapper<T, V> {
    V map(T t);
  }
}
