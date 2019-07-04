package ca.auxility.baseadapter.baseadapter;

import ca.auxility.baseadapter.AbstractItemAdapter;
import ca.auxility.baseadapter.BaseItemAdapter;
import ca.auxility.baseadapter.TestItem;
import ca.auxility.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class ItemAdapterSerializationTestCase extends AdapterSerializationTestCase {
  @Override protected AbstractItemAdapter<TestItem> getAdapter(List<TestItem> items) {
    return new BaseItemAdapter<>(items);
  }
}
