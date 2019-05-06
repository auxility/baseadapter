package com.skiff2011.baseadapter.misc.function;

import android.support.annotation.NonNull;

public interface Function<T, V> {
  V apply(@NonNull T object);
}
