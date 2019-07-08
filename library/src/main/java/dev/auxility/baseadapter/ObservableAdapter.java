package dev.auxility.baseadapter;

import androidx.annotation.NonNull;
import java.io.Serializable;

public interface ObservableAdapter extends Serializable {

  void registerObserver(@NonNull AdapterDataObserver observer);

  void unregisterObserver(@NonNull AdapterDataObserver observer);
}
