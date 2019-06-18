package com.skiff2011.baseadapter.view.viewpager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.view.BaseViewHolder;

class PagerBindingHolder<V extends Item> implements BaseViewHolder<V> {

  private static final String TAG = PagerBindingHolder.class.getSimpleName();

  private ViewDataBinding binding;
  private V vm;

  private PagerBindingHolder(ViewDataBinding binding) {
    this.binding = binding;
  }

  public static <V extends Item> PagerBindingHolder<V> create(LayoutInflater layoutInflater,
      @LayoutRes int resId, ViewGroup p) {
    try {
      return new PagerBindingHolder<>(DataBindingUtil.inflate(layoutInflater, resId, p, true));
    } catch (Throwable e) {
      String resName = layoutInflater.getContext().getResources().getResourceName(resId);
      Log.e(TAG, "Error inflating layout " + resName);
      throw e;
    }
  }

  @Override
  public void bindViewModel(V VM) {
    this.vm = VM;
    binding.setVariable(BR.item, VM);
    binding.executePendingBindings();
  }

  @Override
  public ViewDataBinding getBinding() {
    return binding;
  }

  public V getVM() {
    return vm;
  }
}
