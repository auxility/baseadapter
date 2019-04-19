package eu.theappshop.baseadapter.vmadapter;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;
import eu.theappshop.baseadapter.adapterv2.BaseVmAdapter;
import eu.theappshop.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class VmAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected AbstractVmAdapter<StubVM> provideAdapter(List<StubVM> vms) {
    return new BaseVmAdapter<>(vms);
  }
}
