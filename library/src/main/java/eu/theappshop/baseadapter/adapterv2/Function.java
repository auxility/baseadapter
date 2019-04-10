package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;

public interface Function<T, V> {
  V apply(@NonNull T object);
}
