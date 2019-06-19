package com.skiff2011.baseadapter;

import androidx.annotation.Nullable;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.utils.ListUtils;
import java.util.Comparator;

public class TestItem implements Item, Comparable<TestItem> {

  public final int value;

  public TestItem(int value) {
    this.value = value;
  }

  @Override public int getLayoutId() {
    return 0;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TestItem)) {
      return false;
    }
    TestItem other = (TestItem) obj;
    return this.value == other.value && this.getLayoutId() == other.getLayoutId();
  }

  @Override public int hashCode() {
    return value;
  }

  @Override public int compareTo(TestItem o) {
    return this.value - o.value;
  }

  public static class IntMapper implements ListUtils.Mapper<Integer, TestItem> {

    @Override public TestItem map(Integer integer) {
      return new TestItem(integer);
    }
  }

  public static class StubVmComparator implements Comparator<TestItem> {

    @Override public int compare(TestItem o1, TestItem o2) {
      return o1.value - o2.value;
    }
  }
}
