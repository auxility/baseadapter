package com.skiff2011.baseadapter.view;

import androidx.databinding.ViewDataBinding;
import com.skiff2011.baseadapter.item.Item;

public interface BaseViewHolder<V extends Item> {

  void bindViewModel(V VM);

  ViewDataBinding getBinding();
}
