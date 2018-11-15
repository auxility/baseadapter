package eu.theappshop.baseadapter.vm;

import android.view.ViewGroup;

public interface ViewGroupVM<T extends ViewGroup.LayoutParams> extends VM {

  T getLayoutParams();
}
