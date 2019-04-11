package eu.theappshop.baseadapter;

import android.support.annotation.Nullable;
import eu.theappshop.baseadapter.vm.VM;

public class StubVM implements VM {

  final int value;

  public StubVM(int value) {
    this.value = value;
  }

  @Override public int getLayoutId() {
    return 0;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StubVM)) {
      return false;
    }
    StubVM other = (StubVM) obj;
    return this.value == other.value && this.getLayoutId() == other.getLayoutId();
  }

  @Override public int hashCode() {
    return value;
  }
}
