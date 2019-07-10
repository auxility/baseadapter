package dev.auxility.baseadapter.misc.function;

import androidx.annotation.NonNull;
import dev.auxility.baseadapter.item.Item;

public interface Filter<V extends Item> extends SerializablePredicate<V> {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
