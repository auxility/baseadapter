package eu.theappshop.baseadapter.vmadapter;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.Adapter;
import eu.theappshop.baseadapter.adapterv2.VmAdapter;
import eu.theappshop.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class VmAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected Adapter<StubVM> provideAdapter(List<StubVM> vms) {
    return new VmAdapter<>(vms);
  }
}
