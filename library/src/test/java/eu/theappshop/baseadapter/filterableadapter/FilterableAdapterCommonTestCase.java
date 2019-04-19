package eu.theappshop.baseadapter.filterableadapter;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;
import eu.theappshop.baseadapter.adapterv2.FilterableVmAdapter;
import eu.theappshop.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractVmAdapter<StubVM> provideAdapter(List<StubVM> vms) {
    return new FilterableVmAdapter<>(new TestPredicate(), vms);
  }
}
