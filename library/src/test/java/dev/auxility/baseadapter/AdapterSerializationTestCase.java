package dev.auxility.baseadapter;

import java.util.List;

public class AdapterSerializationTestCase
    extends dev.auxility.baseadapter.general.AdapterSerializationTestCase {
  @Override protected AbstractAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new BaseAdapter<>(items);
  }
}
