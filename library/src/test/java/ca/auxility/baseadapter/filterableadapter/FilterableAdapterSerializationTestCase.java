package ca.auxility.baseadapter.filterableadapter;

import ca.auxility.baseadapter.AbstractItemAdapter;
import ca.auxility.baseadapter.FilterableItemAdapter;
import ca.auxility.baseadapter.TestItem;
import ca.auxility.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class FilterableAdapterSerializationTestCase extends AdapterSerializationTestCase {

  @Override protected AbstractItemAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new FilterableItemAdapter<>(items, new TestPredicate());
  }
}
