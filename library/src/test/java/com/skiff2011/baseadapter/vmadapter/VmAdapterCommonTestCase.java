package com.skiff2011.baseadapter.vmadapter;

import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.BaseVmAdapter;
import com.skiff2011.baseadapter.StubVM;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class VmAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected AbstractVmAdapter<StubVM> provideAdapter(List<StubVM> vms) {
    return new BaseVmAdapter<>(vms);
  }
}
