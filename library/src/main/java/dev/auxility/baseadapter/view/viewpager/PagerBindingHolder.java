package dev.auxility.baseadapter.view.viewpager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import ca.auxility.baseadapter.BR;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.view.BaseViewHolder;

class PagerBindingHolder<V extends Item> implements BaseViewHolder<V> {

  private static final String TAG = PagerBindingHolder.class.getSimpleName();

  private ViewDataBinding binding;
  private V item;

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
  public void bindViewModel(V item) {
    this.item = item;
    binding.setVariable(BR.item, item);
    binding.executePendingBindings();
  }

  @Override
  public ViewDataBinding getBinding() {
    return binding;
  }

  public V getItem() {
    return item;
  }
}
