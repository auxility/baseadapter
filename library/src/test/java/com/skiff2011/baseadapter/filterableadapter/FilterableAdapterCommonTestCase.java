package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractItemAdapter<StubItem> provideAdapter(List<StubItem> vms) {
    return new FilterableItemAdapter<>(vms, new TestPredicate());
  }
}
