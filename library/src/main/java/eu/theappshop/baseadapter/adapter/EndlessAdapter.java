package eu.theappshop.baseadapter.adapter;

import eu.theappshop.baseadapter.vm.VM;
import java.io.Serializable;

public interface EndlessAdapter<V extends VM> extends Serializable {

  void loadMore();

  boolean isLoading();

  void setLoading(boolean loading);

  boolean hasLoadedAll();

  int getThreshold();
}
