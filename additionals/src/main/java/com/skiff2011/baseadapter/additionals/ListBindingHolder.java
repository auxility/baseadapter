package com.skiff2011.baseadapter.additionals;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.view.BaseViewHolder;
import com.skiff2011.baseadapter.vm.VM;

public class ListBindingHolder<V extends VM> implements BaseViewHolder<V> {

  private static final String TAG = ListBindingHolder.class.getSimpleName();

  private ViewDataBinding binding;
  private V vm;

  private ListBindingHolder(ViewDataBinding binding) {
    this.binding = binding;
    binding.getRoot().setTag(this);
  }

  public static <V extends VM> ListBindingHolder<V> create(LayoutInflater layoutInflater,
      @LayoutRes int resId, ViewGroup p) {
    try {
      return new ListBindingHolder<>(DataBindingUtil.inflate(layoutInflater, resId, p, false));
    } catch (Throwable e) {
      String resName = layoutInflater.getContext().getResources().getResourceName(resId);
      Log.e(TAG, "Error inflating layout " + resName);
      throw e;
    }
  }

  @Override public void bindViewModel(V VM) {
    this.vm = VM;
    binding.setVariable(BR.viewModel, VM);
    binding.executePendingBindings();
  }

  @Override public ViewDataBinding getBinding() {
    return binding;
  }

  public V getVm() {
    return vm;
  }
}
