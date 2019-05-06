package com.skiff2011.baseadapter.misc.function;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;

public interface Filter<V extends VM> extends SerializablePredicate<V> {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
