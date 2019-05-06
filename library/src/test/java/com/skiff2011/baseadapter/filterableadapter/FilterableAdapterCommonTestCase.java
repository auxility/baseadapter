package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.FilterableVmAdapter;
import com.skiff2011.baseadapter.StubVM;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractVmAdapter<StubVM> provideAdapter(List<StubVM> vms) {
    return new FilterableVmAdapter<>(vms, new TestPredicate());
  }
}
