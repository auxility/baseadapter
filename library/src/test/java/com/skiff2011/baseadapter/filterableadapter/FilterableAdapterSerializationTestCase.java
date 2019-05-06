package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.FilterableVmAdapter;
import com.skiff2011.baseadapter.StubVM;
import com.skiff2011.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class FilterableAdapterSerializationTestCase extends AdapterSerializationTestCase {

  @Override protected AbstractVmAdapter<StubVM> getAdapter(List<StubVM> vms) {
    return new FilterableVmAdapter<>(vms, new TestPredicate());
  }
}
