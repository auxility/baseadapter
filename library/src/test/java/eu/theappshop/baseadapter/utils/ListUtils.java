package eu.theappshop.baseadapter.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class ListUtils {
  public static <T> List<T> generateList(int count, Mapper<Integer, T> mapper) {
    List<T> list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      list.add(mapper.map(i));
    }
    return list;
  }

  public interface Mapper<T, V> {
    V map(T t);
  }
}
