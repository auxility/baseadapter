package dev.auxility.baseadapter.view;

import androidx.databinding.ViewDataBinding;
import dev.auxility.baseadapter.item.Item;

public interface BaseViewHolder<V extends Item> {

  void bindViewModel(V item);

  ViewDataBinding getBinding();
}
