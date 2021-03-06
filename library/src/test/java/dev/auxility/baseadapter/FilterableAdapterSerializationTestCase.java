package dev.auxility.baseadapter;

import dev.auxility.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class FilterableAdapterSerializationTestCase extends AdapterSerializationTestCase {

  @Override protected AbstractAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new FilterableAdapter<>(items, new TestPredicate());
  }
}
