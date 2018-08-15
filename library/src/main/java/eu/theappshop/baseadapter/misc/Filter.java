package eu.theappshop.baseadapter.misc;

import java.io.Serializable;
import java.util.List;

public interface Filter<T> extends Serializable {
  List<T> filter(List<T> list);
}
