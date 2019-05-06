package com.skiff2011.baseadapter.additionals;

import android.view.ViewGroup;
import com.skiff2011.baseadapter.vm.VM;

public interface ViewGroupVM<T extends ViewGroup.LayoutParams> extends VM {

  T getLayoutParams();
}
