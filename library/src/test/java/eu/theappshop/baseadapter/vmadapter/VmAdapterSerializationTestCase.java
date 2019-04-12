package eu.theappshop.baseadapter.vmadapter;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.Adapter;
import eu.theappshop.baseadapter.adapterv2.VmAdapter;
import eu.theappshop.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class VmAdapterSerializationTestCase extends AdapterSerializationTestCase {
  @Override protected Adapter<StubVM> getAdapter(List<StubVM> vms) {
    return new VmAdapter<>(vms);
  }
}
