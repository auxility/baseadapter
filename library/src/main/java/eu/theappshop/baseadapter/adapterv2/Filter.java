package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;

public interface Filter<V extends VM> extends Predicate<V>, Serializable {
  Filter<V> thenFiltering(@NonNull Filter<V> nextFilter);
}
