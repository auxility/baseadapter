package dev.auxility.baseadapter.additionals;

import android.view.ViewGroup;
import dev.auxility.baseadapter.item.Item;

public interface ViewGroupItem<T extends ViewGroup.LayoutParams> extends Item {

  T getLayoutParams();
}
