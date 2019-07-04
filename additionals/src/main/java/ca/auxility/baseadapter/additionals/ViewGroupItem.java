package ca.auxility.baseadapter.additionals;

import android.view.ViewGroup;
import ca.auxility.baseadapter.item.Item;

public interface ViewGroupItem<T extends ViewGroup.LayoutParams> extends Item {

  T getLayoutParams();
}
