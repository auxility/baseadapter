package com.skiff2011.baseadapter;

import android.support.annotation.Nullable;
import com.skiff2011.baseadapter.utils.ListUtils;
import com.skiff2011.baseadapter.vm.VM;
import java.util.Comparator;

public class StubVM implements VM, Comparable<StubVM> {

  public final int value;

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

  @Override public int compareTo(StubVM o) {
    return this.value - o.value;
  }

  public static class IntMapper implements ListUtils.Mapper<Integer, StubVM> {

    @Override public StubVM map(Integer integer) {
      return new StubVM(integer);
    }
  }

  public static class StubVmComparator implements Comparator<StubVM> {

    @Override public int compare(StubVM o1, StubVM o2) {
      return o1.value - o2.value;
    }
  }
}
