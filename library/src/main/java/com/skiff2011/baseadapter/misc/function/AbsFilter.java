package com.skiff2011.baseadapter.misc.function;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;

public abstract class AbsFilter<V extends VM> implements Filter<V> {

  @Override public Filter<V> thenFiltering(@NonNull final Filter<V> nextFilter) {
    return new AbsFilter<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return AbsFilter.this.apply(object) && nextFilter.apply(object);
      }
    };
  }
}
