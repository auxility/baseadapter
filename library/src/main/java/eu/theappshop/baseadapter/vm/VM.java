package eu.theappshop.baseadapter.vm;

import android.support.annotation.LayoutRes;
import java.io.Serializable;

public interface VM extends Serializable {

  @LayoutRes
  int getLayoutId();
}
