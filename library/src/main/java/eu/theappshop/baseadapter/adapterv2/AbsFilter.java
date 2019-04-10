package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;

public abstract class AbsFilter<V extends VM> implements Filter<V> {

  @Override public Filter<V> thenFiltering(@NonNull final Filter<V> nextFilter) {
    return new AbsFilter<V>() {
      @Override public Boolean apply(@NonNull V object) {
        return AbsFilter.this.apply(object) && nextFilter.apply(object);
      }
    };
  }
}
