package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.vm.VM;
import java.io.Serializable;

public interface ObservableAdapter<V extends VM> extends Serializable {

  void registerObserver(@NonNull AdapterDataObserver<V> observer);

  void unregisterObserver(@NonNull AdapterDataObserver<V> observer);
}
