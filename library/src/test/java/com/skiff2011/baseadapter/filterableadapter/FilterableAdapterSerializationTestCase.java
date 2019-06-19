package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class FilterableAdapterSerializationTestCase extends AdapterSerializationTestCase {

  @Override protected AbstractItemAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new FilterableItemAdapter<>(items, new TestPredicate());
  }
}
