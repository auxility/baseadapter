package ca.auxility.baseadapter.baseadapter;

import ca.auxility.baseadapter.AbstractItemAdapter;
import ca.auxility.baseadapter.BaseItemAdapter;
import ca.auxility.baseadapter.TestItem;
import ca.auxility.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class ItemAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected AbstractItemAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new BaseItemAdapter<>(vms);
  }
}
