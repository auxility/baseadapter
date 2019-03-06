package eu.theappshop.baseadapter.adapterv2;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;

public interface ObservableAdapter<V extends VM> extends Serializable {

  void registerObserver(@NonNull AdapterDataObserver<V> observer);

  void unregisterObserver(@NonNull AdapterDataObserver<V> observer);
}
