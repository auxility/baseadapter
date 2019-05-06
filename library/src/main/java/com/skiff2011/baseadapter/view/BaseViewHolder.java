package com.skiff2011.baseadapter.view;

import android.databinding.ViewDataBinding;
import com.skiff2011.baseadapter.vm.VM;

public interface BaseViewHolder<V extends VM> {

  void bindViewModel(V VM);

  ViewDataBinding getBinding();
}
