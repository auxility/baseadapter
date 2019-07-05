package ca.auxility.baseadapter;

import ca.auxility.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new FilterableAdapter<>(vms, new TestPredicate());
  }
}
