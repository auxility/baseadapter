package ca.auxility.baseadapter.view;

import androidx.databinding.ViewDataBinding;
import ca.auxility.baseadapter.item.Item;

public interface BaseViewHolder<V extends Item> {

  void bindViewModel(V item);

  ViewDataBinding getBinding();
}
