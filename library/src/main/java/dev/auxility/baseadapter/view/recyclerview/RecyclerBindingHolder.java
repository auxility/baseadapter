package dev.auxility.baseadapter.view.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import ca.auxility.baseadapter.BR;
import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.view.BaseViewHolder;

class RecyclerBindingHolder<V extends Item> extends RecyclerView.ViewHolder
    implements BaseViewHolder<V> {

  private static final String TAG = RecyclerBindingHolder.class.getSimpleName();
  private ViewDataBinding binding;

  private RecyclerBindingHolder(ViewDataBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public static <V extends Item> RecyclerBindingHolder<V> create(LayoutInflater layoutInflater,
      @LayoutRes int resId, ViewGroup p) {
    try {
      return new RecyclerBindingHolder<>(DataBindingUtil.inflate(layoutInflater, resId, p, false));
    } catch (Throwable e) {
      String resName = layoutInflater.getContext().getResources().getResourceName(resId);
      Log.e(TAG, "Error inflating layout " + resName);
      throw e;
    }
  }

  @Override
  public void bindViewModel(V VM) {
    binding.setVariable(BR.item, VM);
    binding.executePendingBindings();
  }

  @Override
  public ViewDataBinding getBinding() {
    return binding;
  }
}
