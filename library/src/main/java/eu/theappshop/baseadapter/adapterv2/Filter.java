package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;

public interface Filter<V extends VM> extends SerializablePredicate<V> {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
