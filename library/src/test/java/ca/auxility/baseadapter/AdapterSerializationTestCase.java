package ca.auxility.baseadapter;

import java.util.List;

public class AdapterSerializationTestCase
    extends ca.auxility.baseadapter.general.AdapterSerializationTestCase {
  @Override protected AbstractAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new BaseAdapter<>(items);
  }
}
