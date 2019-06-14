package com.skiff2011.baseadapter.item;

import android.support.annotation.LayoutRes;
import java.io.Serializable;

public interface Item extends Serializable {

  @LayoutRes
  int getLayoutId();
}
