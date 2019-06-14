package com.skiff2011.baseadapter;

import android.support.annotation.Nullable;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.utils.ListUtils;
import java.util.Comparator;

public class StubItem implements Item, Comparable<StubItem> {

  public final int value;

  public StubItem(int value) {
    this.value = value;
  }

  @Override public int getLayoutId() {
    return 0;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StubItem)) {
      return false;
    }
    StubItem other = (StubItem) obj;
    return this.value == other.value && this.getLayoutId() == other.getLayoutId();
  }

  @Override public int hashCode() {
    return value;
  }

  @Override public int compareTo(StubItem o) {
    return this.value - o.value;
  }

  public static class IntMapper implements ListUtils.Mapper<Integer, StubItem> {

    @Override public StubItem map(Integer integer) {
      return new StubItem(integer);
    }
  }

  public static class StubVmComparator implements Comparator<StubItem> {

    @Override public int compare(StubItem o1, StubItem o2) {
      return o1.value - o2.value;
    }
  }
}
