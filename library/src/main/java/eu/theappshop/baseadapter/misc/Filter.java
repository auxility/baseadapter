package eu.theappshop.baseadapter.misc;

import java.util.List;

public interface Filter<T> {

    List<T> filter(List<T> list);

}
