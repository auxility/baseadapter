package dev.auxility.baseadapter.misc.function;

import androidx.annotation.NonNull;
import dev.auxility.baseadapter.item.Item;

public abstract class AbsFilter<V extends Item> implements Filter<V> {

  @Override public Filter<V> thenFiltering(@NonNull final Filter<V> nextFilter) {
    return new AbsFilter<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return AbsFilter.this.apply(object) && nextFilter.apply(object);
      }
    };
  }
}
