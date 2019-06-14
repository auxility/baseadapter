package com.skiff2011.baseadapter.additionals;

import android.view.ViewGroup;
import com.skiff2011.baseadapter.item.Item;

public interface ViewGroupItem<T extends ViewGroup.LayoutParams> extends Item {

  T getLayoutParams();
}
