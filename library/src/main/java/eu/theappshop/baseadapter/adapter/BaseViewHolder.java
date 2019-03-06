package eu.theappshop.baseadapter.adapter;

import android.databinding.ViewDataBinding;
import eu.theappshop.baseadapter.vm.VM;

public interface BaseViewHolder<V extends VM> {

  void bindViewModel(V VM);

  ViewDataBinding getBinding();
}
