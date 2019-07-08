package dev.auxility.baseadapter.additionals;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.skiff2011.baseadapter.BR;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.view.BaseViewHolder;

public class ListBindingHolder<V extends Item> implements BaseViewHolder<V> {

  private static final String TAG = ListBindingHolder.class.getSimpleName();

  private ViewDataBinding binding;
  private V vm;

  private ListBindingHolder(ViewDataBinding binding) {
    this.binding = binding;
    binding.getRoot().setTag(this);
  }

  public static <V extends Item> ListBindingHolder<V> create(LayoutInflater layoutInflater,
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
    binding.setVariable(BR.item, VM);
    binding.executePendingBindings();
  }

  @Override public ViewDataBinding getBinding() {
    return binding;
  }

  public V getVm() {
    return vm;
  }
}
