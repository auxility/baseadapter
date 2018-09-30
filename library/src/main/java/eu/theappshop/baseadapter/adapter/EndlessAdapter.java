package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface EndlessAdapter<V extends VM> extends Serializable {

  void loadMore();

  void addLoadedItems(List<V> vms);

  boolean isLoading();

  boolean hasLoadedAll();

  int getThreshold();
}
