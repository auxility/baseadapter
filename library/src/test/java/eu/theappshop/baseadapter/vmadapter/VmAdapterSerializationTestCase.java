package eu.theappshop.baseadapter.vmadapter;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;
import eu.theappshop.baseadapter.adapterv2.BaseVmAdapter;
import eu.theappshop.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class VmAdapterSerializationTestCase extends AdapterSerializationTestCase {
  @Override protected AbstractVmAdapter<StubVM> getAdapter(List<StubVM> vms) {
    return new BaseVmAdapter<>(vms);
  }
}
