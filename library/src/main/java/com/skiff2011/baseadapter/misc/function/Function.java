package com.skiff2011.baseadapter.misc.function;

import androidx.annotation.NonNull;

public interface Function<T, V> {
  V apply(@NonNull T object);
}
