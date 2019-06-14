package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class FilterableAdapterSerializationTestCase extends AdapterSerializationTestCase {

  @Override protected AbstractItemAdapter<StubItem> getAdapter(List<StubItem> vms) {
    return new FilterableItemAdapter<>(vms, new TestPredicate());
  }
}
