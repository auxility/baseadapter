package ca.auxility.baseadapter.filterableadapter;

import ca.auxility.baseadapter.AbstractItemAdapter;
import ca.auxility.baseadapter.FilterableItemAdapter;
import ca.auxility.baseadapter.TestItem;
import ca.auxility.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class FilterableAdapterCommonTestCase extends AdapterCommonTestCase {

  @Override protected AbstractItemAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new FilterableItemAdapter<>(vms, new TestPredicate());
  }
}
