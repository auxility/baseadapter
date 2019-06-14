package com.skiff2011.baseadapter;

import android.support.annotation.NonNull;
import java.io.Serializable;

public interface ObservableAdapter extends Serializable {

  void registerObserver(@NonNull AdapterDataObserver observer);

  void unregisterObserver(@NonNull AdapterDataObserver observer);
}
