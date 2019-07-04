package ca.auxility.baseadapter.item;

import androidx.annotation.LayoutRes;
import java.io.Serializable;

public interface Item extends Serializable {

  @LayoutRes
  int getLayoutId();
}
