package com.skiff2011.baseadapter.misc.function;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.item.Item;

public interface Filter<V extends Item> extends SerializablePredicate<V> {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
