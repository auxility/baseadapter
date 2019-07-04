package ca.auxility.baseadapter.misc.function;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.item.Item;

public interface Filter<V extends Item> extends SerializablePredicate<V> {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
