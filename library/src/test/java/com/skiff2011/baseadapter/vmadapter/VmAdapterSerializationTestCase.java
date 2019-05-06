package com.skiff2011.baseadapter.vmadapter;

import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.BaseVmAdapter;
import com.skiff2011.baseadapter.StubVM;
import com.skiff2011.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class VmAdapterSerializationTestCase extends AdapterSerializationTestCase {
  @Override protected AbstractVmAdapter<StubVM> getAdapter(List<StubVM> vms) {
    return new BaseVmAdapter<>(vms);
  }
}
