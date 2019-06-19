package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractItemAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new FilterableItemAdapter<>(vms, new TestPredicate());
  }
}
